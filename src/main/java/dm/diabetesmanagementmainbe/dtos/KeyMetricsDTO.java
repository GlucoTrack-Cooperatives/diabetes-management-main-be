package dm.diabetesmanagementmainbe.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeyMetricsDTO {
    private double timeInRange;
    private double timeBelowRange;
    private double averageGlucose;
}
