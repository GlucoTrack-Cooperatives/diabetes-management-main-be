package dm.diabetesmanagementmainbe.controller.patient.dto.settings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientSettingsDTO {
    private Integer targetRangeHigh;
    private Integer targetRangeLow;
    private Float insulinCarbRatio;
    private Float correctionFactor;
    private Integer lowThreshold;
    private Integer criticalLowThreshold;
    private Integer highThreshold;
    private Integer criticalHighThreshold;
}
