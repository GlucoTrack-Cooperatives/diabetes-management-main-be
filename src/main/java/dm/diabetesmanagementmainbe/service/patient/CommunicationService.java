package dm.diabetesmanagementmainbe.service.patient;

import dm.diabetesmanagementmainbe.controller.patient.dto.communication.AlertDTO;
import dm.diabetesmanagementmainbe.controller.patient.dto.communication.ChatMessageDTO;
import dm.diabetesmanagementmainbe.controller.patient.dto.communication.ChatThreadDTO;
import dm.diabetesmanagementmainbe.dao.model.communication.ChatMessage;
import dm.diabetesmanagementmainbe.dao.repository.communication.AlertRepository;
import dm.diabetesmanagementmainbe.dao.repository.communication.ChatMessageRepository;
import dm.diabetesmanagementmainbe.dao.repository.communication.ChatThreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommunicationService {

    private final ChatThreadRepository chatThreadRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final AlertRepository alertRepository;

    public List<ChatThreadDTO> findChatThreads(UUID patientId) {
        return chatThreadRepository.findByPatientId(patientId)
                .stream()
                .map(thread -> {
                    String lastMessage = thread.getMessages().stream()
                            .max(Comparator.comparing(ChatMessage::getTimestamp))
                            .map(ChatMessage::getContent)
                            .orElse("");
                    return ChatThreadDTO.builder()
                            .id(thread.getId())
                            .physicianName(thread.getPhysician().getFirstName())
                            .lastMessage(lastMessage)
                            .build();
                })
                .toList();
    }

    public List<ChatMessageDTO> findChatMessages(UUID threadId) {
        return chatMessageRepository.findByChatThreadIdOrderByTimestampDesc(threadId)
                .stream()
                .map(message -> ChatMessageDTO.builder()
                        .id(message.getId())
                        .content(message.getContent())
                        .senderId(message.getSender().getId())
                        .timestamp(message.getTimestamp())
                        .build())
                .toList();
    }

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
}
