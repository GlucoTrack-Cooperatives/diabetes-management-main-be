package dm.diabetesmanagementmainbe.controller.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStatsDTO {
    private double timeInRange;
    private double timeBelowRange;
    private double averageGlucose;
}
