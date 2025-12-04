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
public class HealthEventDTO {
    private UUID id;
    private LocalDateTime timestamp;
    //private HealthEventCategory category;
    private Integer severity;
    private String note;
    private LocalDateTime createdAt;
}
