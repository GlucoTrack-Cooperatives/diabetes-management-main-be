package dm.diabetesmanagementmainbe.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDTO {
    private UUID id;
    private String content;
    private LocalDateTime timestamp;
    private Boolean isRead;
    private LocalDateTime createdAt;
}
