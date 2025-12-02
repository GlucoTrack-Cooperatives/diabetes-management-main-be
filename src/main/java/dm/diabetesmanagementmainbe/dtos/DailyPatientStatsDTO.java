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
public class DailyPatientStatsDTO {
    private UUID id;
    private LocalDate date;
    private Float averageGlucose;
    private Float timeInRangePercent;
    private Float timeBelowRangePercent;
    private Float timeAboveRangePercent;
    private Integer totalCarbs;
    private Float totalInsulin;
    private Integer riskScore;
    private LocalDateTime createdAt;
}
