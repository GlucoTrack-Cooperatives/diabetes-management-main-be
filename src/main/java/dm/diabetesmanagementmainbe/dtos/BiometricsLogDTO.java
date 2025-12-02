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
public class BiometricsLogDTO {
    private UUID id;
    private LocalDate date;
    private Float weightKg;
    private Integer waterIntakeMl;
    private LocalDateTime createdAt;
}
