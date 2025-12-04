package dm.diabetesmanagementmainbe.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityLogDTO {
    private UUID id;
    private LocalDate date;
    private Integer stepCount;
    private Float activeEnergyBurned;
    private Integer exerciseMinutes;
    private Integer sleepDurationMinutes;
    private LocalDateTime sleepStartTime;
    private LocalDateTime sleepEndTime;
    private LocalDateTime createdAt;
}
