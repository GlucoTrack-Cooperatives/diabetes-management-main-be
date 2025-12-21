package dm.diabetesmanagementmainbe.dao.model;

import dm.diabetesmanagementmainbe.dao.model.user.Patient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "daily_patient_stat")
public class DailyPatientStat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "average_glucose", nullable = false)
    private Float averageGlucose;

    @NotNull
    @Column(name = "time_in_range", nullable = false)
    private Float timeInRange;

    @NotNull
    @Column(name = "time_below_range", nullable = false)
    private Float timeBelowRange;

    @NotNull
    @ColumnDefault("0.0")
    @Column(name = "time_above_range_percent", nullable = false)
    private Float timeAboveRangePercent;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "total_carbs", nullable = false)
    private Integer totalCarbs;

    @NotNull
    @ColumnDefault("0.0")
    @Column(name = "total_insulin", nullable = false)
    private Float totalInsulin;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "risk_score", nullable = false)
    private Integer riskScore;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    private void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

}
