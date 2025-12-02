package dm.diabetesmanagementmainbe.dtos;

import dm.diabetesmanagementmainbe.enums.AlertType;
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
public class AlertDTO {
    private UUID id;
    private LocalDateTime timestamp;
    private AlertType type;
    private String message;
    private Boolean isAcknowledged;
    private LocalDateTime acknowledgedAt;
    private LocalDateTime createdAt;
}
