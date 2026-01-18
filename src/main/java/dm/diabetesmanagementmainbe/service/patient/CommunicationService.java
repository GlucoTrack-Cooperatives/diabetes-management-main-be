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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @Transactional(readOnly = true)
    public List<ChatThreadDTO> findChatThreads(UUID userId) {
        // 1. Log the input to ensure the method is actually called and the ID is correct
        log.info("Finding chat threads for userId: {}", userId);

        // Try to find threads where user is patient
        List<ChatThread> threads = chatThreadRepository.findByPatientId(userId);
        log.info("Query by PatientID result count: {}", threads != null ? threads.size() : "null");

        // If empty, try to find threads where user is physician
        if (threads == null || threads.isEmpty()) {
            log.info("No threads found as patient. Checking as physician...");
            threads = chatThreadRepository.findByPhysicianId(userId);
            log.info("Query by PhysicianID result count: {}", threads != null ? threads.size() : "null");
        }

        // Safety check before streaming
        if (threads == null || threads.isEmpty()) {
            log.warn("No threads found for userId: {} in either role.", userId);
            return Collections.emptyList();
        }

        return threads.stream()
                .map(thread -> {
                    // 2. Log inside the loop to inspect specific thread data
                    log.debug("Processing Thread ID: {}", thread.getId());

                    ChatMessage lastMsg = thread.getMessages().stream()
                            .max(Comparator.comparing(ChatMessage::getTimestamp))
                            .orElse(null);

                    String lastMessageContent = lastMsg != null ? lastMsg.getContent() : "";
                    Instant lastMessageTime = lastMsg != null ? lastMsg.getTimestamp() : thread.getCreatedAt();

                    String participantName;

                    // 3. Log the IDs being compared to debug the "Participant Name" logic
                    UUID patientId = thread.getPatient().getId();
                    UUID physicianId = thread.getPhysician().getId();
                    log.debug("Comparing Input UserID: {} vs PatientID: {} vs PhysicianID: {}", userId, patientId, physicianId);

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
        ChatThread thread = chatThreadRepository.findById(threadId)
                .orElseThrow(() -> new RuntimeException("Thread not found"));

        User sender = userRepository.findById(messageDTO.getSenderId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ChatMessage message = new ChatMessage();
        message.setChatThread(thread);
        message.setSender(sender);
        message.setContent(messageDTO.getContent());
        message.setTimestamp(Instant.now());

        ChatMessage savedMessage = chatMessageRepository.save(message);

        // Update the thread's last message time
        thread.setLastMessageAt(savedMessage.getTimestamp());

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
