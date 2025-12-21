package dm.diabetesmanagementmainbe.controller.patient.dto.log;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class InsulinDoseRequest {

    @NotNull
    private UUID medicationId;

    @NotNull
    private Double units;
}
