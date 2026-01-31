package dm.diabetesmanagementmainbe.service.patient;

import dm.diabetesmanagementmainbe.controller.patient.dto.communication.AlertDTO;
import dm.diabetesmanagementmainbe.controller.patient.dto.communication.ChatMessageDTO;
import dm.diabetesmanagementmainbe.controller.patient.dto.communication.ChatThreadDTO;
import dm.diabetesmanagementmainbe.dao.model.communication.ChatMessage;
import dm.diabetesmanagementmainbe.dao.model.communication.ChatThread;
import dm.diabetesmanagementmainbe.dao.model.user.Patient;
import dm.diabetesmanagementmainbe.dao.model.user.Physician;
import dm.diabetesmanagementmainbe.dao.model.user.User;
import dm.diabetesmanagementmainbe.dao.repository.communication.AlertRepository;
import dm.diabetesmanagementmainbe.dao.repository.communication.ChatMessageRepository;
import dm.diabetesmanagementmainbe.dao.repository.communication.ChatThreadRepository;
import dm.diabetesmanagementmainbe.dao.repository.user.UserRepository;
import dm.diabetesmanagementmainbe.service.notifications.NotificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CommunicationService {
    private static final Logger log = LoggerFactory.getLogger(CommunicationService.class);

    private final ChatThreadRepository chatThreadRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final AlertRepository alertRepository;
    private final UserRepository userRepository;

    // Injected Notification Service (optional if Firebase is disabled)
    @Nullable
    private final NotificationService notificationService;

    @Transactional(readOnly = true)
    public List<ChatThreadDTO> findChatThreads(UUID userId) {
        log.info("Finding chat threads for userId: {}", userId);

        // Try to find threads where user is patient
        List<ChatThread> threads = chatThreadRepository.findByPatientId(userId);

        // If empty, try to find threads where user is physician
        if (threads == null || threads.isEmpty()) {
            threads = chatThreadRepository.findByPhysicianId(userId);
        }

        if (threads == null || threads.isEmpty()) {
            log.warn("No threads found for userId: {} in either role.", userId);
            return Collections.emptyList();
        }

        return threads.stream()
                .map(thread -> {
                    ChatMessage lastMsg = thread.getMessages().stream()
                            .max(Comparator.comparing(ChatMessage::getTimestamp))
                            .orElse(null);

                    String lastMessageContent = lastMsg != null ? lastMsg.getContent() : "";
                    Instant lastMessageTime = lastMsg != null ? lastMsg.getTimestamp() : thread.getCreatedAt();

                    String participantName;
                    UUID patientId = thread.getPatient().getId();

                    if (patientId.equals(userId)) {
                        participantName = thread.getPhysician().getFirstName() + " " + thread.getPhysician().getSurname();
                    } else {
                        participantName = thread.getPatient().getFirstName() + " " + thread.getPatient().getSurname();
                    }

                    return ChatThreadDTO.builder()
                            .id(thread.getId())
                            .physicianName(thread.getPhysician().getFirstName())
                            .participantName(participantName)
                            .lastMessage(lastMessageContent)
                            .lastMessageTime(lastMessageTime)
                            .build();
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ChatMessageDTO> findChatMessages(UUID threadId) {
        return chatMessageRepository.findByChatThreadIdOrderByTimestampDesc(threadId)
                .stream()
                .map(message -> ChatMessageDTO.builder()
                        .id(message.getId())
                        .threadId(message.getChatThread().getId())
                        .content(message.getContent())
                        .senderId(message.getSender().getId())
                        .timestamp(message.getTimestamp())
                        .build())
                .toList();
    }

    @Transactional
    public ChatMessageDTO saveMessage(UUID threadId, ChatMessageDTO messageDTO) {
        // 1. Validate Entities
        ChatThread thread = chatThreadRepository.findById(threadId)
                .orElseThrow(() -> new RuntimeException("Thread not found"));

        User sender = userRepository.findById(messageDTO.getSenderId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Save Message
        ChatMessage message = new ChatMessage();
        message.setChatThread(thread);
        message.setSender(sender);
        message.setContent(messageDTO.getContent());
        message.setTimestamp(Instant.now());

        ChatMessage savedMessage = chatMessageRepository.save(message);

        // 3. Update Thread
        thread.setLastMessageAt(savedMessage.getTimestamp());

        // 4. Send Notification (if Firebase is enabled)
        if (notificationService != null) {
            try {
                // Determine who receives the notification (the person who is NOT the sender)
                User recipient;
                if (thread.getPatient().getId().equals(sender.getId())) {
                    recipient = thread.getPhysician();
                } else {
                    recipient = thread.getPatient();
                }

                // Create payload for Frontend routing
                // Key 'type'='chat' matches the logic in your Flutter FcmService
                Map<String, String> payload = Map.of(
                        "type", "chat",
                        "threadId", thread.getId().toString(),
                        "senderId", sender.getId().toString()
                );

                notificationService.sendPushToUser(
                        recipient.getId(),
                        "New message from " + sender.getFirstName(),
                        savedMessage.getContent(),
                        payload
                );
            } catch (Exception e) {
                log.error("Failed to send chat notification for thread {}", threadId, e);
                // We swallow the exception here so the message is still saved even if notification fails
            }
        } else {
            log.debug("Firebase messaging is disabled. Skipping chat notification.");
        }

        return ChatMessageDTO.builder()
                .id(savedMessage.getId())
                .threadId(savedMessage.getChatThread().getId())
                .content(savedMessage.getContent())
                .senderId(savedMessage.getSender().getId())
                .timestamp(savedMessage.getTimestamp())
                .build();
    }

    @Transactional(readOnly = true)
    public List<AlertDTO> findActiveAlerts(UUID patientId) {
        return alertRepository.findByPatientIdAndIsAcknowledgedFalse(patientId)
                .stream()
                .map(alert -> AlertDTO.builder()
                        .id(alert.getId())
                        .message(alert.getMessage())
                        .severity(alert.getSeverity())
                        .build())
                .toList();
    }

    @Transactional
    public void createInitialThread(Patient patient, Physician physician) {
        if (!chatThreadRepository.existsByPatientAndPhysician(patient, physician)) {
            ChatThread chatThread = new ChatThread();
            chatThread.setPatient(patient);
            chatThread.setPhysician(physician);
            chatThread.setCreatedAt(Instant.now());
            chatThreadRepository.save(chatThread);
        }
    }
}