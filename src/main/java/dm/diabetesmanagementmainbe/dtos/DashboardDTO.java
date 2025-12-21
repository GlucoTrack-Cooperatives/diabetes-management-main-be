package dm.diabetesmanagementmainbe.dtos;

import dm.diabetesmanagementmainbe.dao.model.tracker.GlucoseReading;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {
    private GlucoseReading realTimeGlucose;
    private List<GlucoseReading> glucoseReadings;
    private KeyMetricsDTO keyMetrics;
}
