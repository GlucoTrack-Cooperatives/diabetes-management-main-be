package dm.diabetesmanagementmainbe.dao.model.settings;

import dm.diabetesmanagementmainbe.dao.model.user.Patient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "patient_clinical_setting")
public class PatientClinicalSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NotNull
    @Column(name = "target_range_low", nullable = false)
    private Integer targetRangeLow;

    @NotNull
    @Column(name = "target_range_high", nullable = false)
    private Integer targetRangeHigh;

    @NotNull
    @Column(name = "insulin_carb_ratio", nullable = false)
    private Float insulinCarbRatio;

    @NotNull
    @Column(name = "correction_factor", nullable = false)
    private Float correctionFactor;

    @NotNull
    @ColumnDefault("70")
    @Column(name = "low_threshold", nullable = false)
    private Integer lowThreshold;

    @NotNull
    @ColumnDefault("54")
    @Column(name = "critical_low_threshold", nullable = false)
    private Integer criticalLowThreshold;

    @NotNull
    @ColumnDefault("180")
    @Column(name = "high_threshold", nullable = false)
    private Integer highThreshold;

    @NotNull
    @ColumnDefault("250")
    @Column(name = "critical_high_threshold", nullable = false)
    private Integer criticalHighThreshold;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    private void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

}
