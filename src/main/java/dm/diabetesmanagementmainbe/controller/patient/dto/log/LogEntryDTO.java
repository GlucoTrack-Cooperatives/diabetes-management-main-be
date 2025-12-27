package dm.diabetesmanagementmainbe.controller.patient.dto.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogEntryDTO {
    private String type; // "Food" or "Insulin"
    private Instant timestamp;
    private String description;
    private String carbs;
    private String calories;
}
