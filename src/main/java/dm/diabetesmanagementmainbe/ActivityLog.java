package dm.diabetesmanagementmainbe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getStepCount() {
        return stepCount;
    }

    public void setStepCount(Integer stepCount) {
        this.stepCount = stepCount;
    }

    public Float getActiveEnergyBurned() {
        return activeEnergyBurned;
    }

    public void setActiveEnergyBurned(Float activeEnergyBurned) {
        this.activeEnergyBurned = activeEnergyBurned;
    }

    public Integer getExerciseMinutes() {
        return exerciseMinutes;
    }

    public void setExerciseMinutes(Integer exerciseMinutes) {
        this.exerciseMinutes = exerciseMinutes;
    }

    public Integer getSleepDurationMinutes() {
        return sleepDurationMinutes;
    }

    public void setSleepDurationMinutes(Integer sleepDurationMinutes) {
        this.sleepDurationMinutes = sleepDurationMinutes;
    }

    public Instant getSleepStartTime() {
        return sleepStartTime;
    }

    public void setSleepStartTime(Instant sleepStartTime) {
        this.sleepStartTime = sleepStartTime;
    }

    public Instant getSleepEndTime() {
        return sleepEndTime;
    }

    public void setSleepEndTime(Instant sleepEndTime) {
        this.sleepEndTime = sleepEndTime;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

}