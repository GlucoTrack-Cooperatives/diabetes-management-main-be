package dm.diabetesmanagementmainbe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "activity_log")
public class ActivityLog {
    @Id
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "step_count", nullable = false)
    private Integer stepCount;

    @NotNull
    @ColumnDefault("0.0")
    @Column(name = "active_energy_burned", nullable = false)
    private Float activeEnergyBurned;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "exercise_minutes", nullable = false)
    private Integer exerciseMinutes;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "sleep_duration_minutes", nullable = false)
    private Integer sleepDurationMinutes;

    @Column(name = "sleep_start_time")
    private Instant sleepStartTime;

    @Column(name = "sleep_end_time")
    private Instant sleepEndTime;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

}