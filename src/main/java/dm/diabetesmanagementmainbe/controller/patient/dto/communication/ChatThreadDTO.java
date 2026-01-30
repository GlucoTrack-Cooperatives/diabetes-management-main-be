package dm.diabetesmanagementmainbe.controller.patient.dto.communication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatThreadDTO {
    private UUID id;
    private String physicianName;
    private String participantName;
    private String lastMessage;
    private Instant lastMessageTime;
}
