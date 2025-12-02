package dm.diabetesmanagementmainbe.dtos;

import dm.diabetesmanagementmainbe.enums.GlucoseSource;
import dm.diabetesmanagementmainbe.enums.GlucoseTrend;
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
public class GlucoseReadingDTO {
    private UUID id;
    private LocalDateTime timestamp;
    private Integer value;
    private GlucoseTrend trend;
    private GlucoseSource source;
    private LocalDateTime createdAt;
}
