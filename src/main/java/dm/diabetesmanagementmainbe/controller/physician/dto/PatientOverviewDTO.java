package dm.diabetesmanagementmainbe.controller.physician.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientOverviewDTO {
    private UUID id;
    private String fullName;
    private Integer age;
    private String email;
    private String phoneNumber;

    // Latest Stats
    private Integer latestGlucoseValue;
    private String latestGlucoseTrend;
    private Instant latestGlucoseTimestamp;

    // Status
    private Boolean isPhysicianConfirmed;
}