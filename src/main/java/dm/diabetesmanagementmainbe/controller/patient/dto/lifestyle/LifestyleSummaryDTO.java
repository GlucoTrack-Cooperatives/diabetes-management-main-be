package dm.diabetesmanagementmainbe.controller.patient.dto.lifestyle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LifestyleSummaryDTO {
    private Integer sleepDurationMinutes;
    private Integer activitySteps;
    private Float weight;
    private Integer waterGlasses;
}
