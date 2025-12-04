package dm.diabetesmanagementmainbe.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicationDTO {
    private UUID id;
    private String medicationName;
    private Float dosageMg;
    //private MedicationFrequency frequency;
    private LocalDate startDate;
    private LocalDate endDate;
    private String sideEffects;
    private String interactionWarnings;
    private LocalDateTime createdAt;
}
