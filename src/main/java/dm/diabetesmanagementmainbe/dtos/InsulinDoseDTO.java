package dm.diabetesmanagementmainbe.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsulinDoseDTO {
    private UUID id;
    private LocalDateTime timestamp;
    //private InsulinType insulinType;
    private Float units;
    private Boolean isCorrection;
    private LocalDateTime createdAt;
}
