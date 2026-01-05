package dm.diabetesmanagementmainbe.controller.patient.dto.log;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MedicationDTO {
    private UUID id;
    private String name;
    private String type;
}