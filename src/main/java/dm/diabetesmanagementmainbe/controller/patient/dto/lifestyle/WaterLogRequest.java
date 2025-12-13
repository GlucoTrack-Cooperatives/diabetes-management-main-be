package dm.diabetesmanagementmainbe.controller.patient.dto.lifestyle;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WaterLogRequest {

    @NotNull
    private Integer glassesCount;
}
