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
public class PatientClinicalSettingsDTO {
    private UUID id;
    private Integer targetRangeLow;
    private Integer targetRangeHigh;
    private Float insulinCarbRatio;
    private Float correctionFactor;
    private Float basalRateDaily;
    private Integer hypoThresholdMgDl;
    private Integer hyperThresholdMgDl;
    private Float sickDayFactor;
    private Float exerciseFactor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
