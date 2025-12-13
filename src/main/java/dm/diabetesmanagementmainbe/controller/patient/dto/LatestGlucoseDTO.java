package dm.diabetesmanagementmainbe.controller.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LatestGlucoseDTO {
    private Integer value;
    private String trend;
    private Instant timestamp;
}
