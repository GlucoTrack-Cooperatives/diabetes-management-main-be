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
@Table(name = "daily_patient_stat")
public class DailyPatientStat {
    @Id
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "average_glucose", nullable = false)
    private Float averageGlucose;

    @NotNull
    @Column(name = "time_in_range_percent", nullable = false)
    private Float timeInRangePercent;

    @NotNull
    @Column(name = "time_below_range_percent", nullable = false)
    private Float timeBelowRangePercent;

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

    public Float getAverageGlucose() {
        return averageGlucose;
    }

    public void setAverageGlucose(Float averageGlucose) {
        this.averageGlucose = averageGlucose;
    }

    public Float getTimeInRangePercent() {
        return timeInRangePercent;
    }

    public void setTimeInRangePercent(Float timeInRangePercent) {
        this.timeInRangePercent = timeInRangePercent;
    }

    public Float getTimeBelowRangePercent() {
        return timeBelowRangePercent;
    }

    public void setTimeBelowRangePercent(Float timeBelowRangePercent) {
        this.timeBelowRangePercent = timeBelowRangePercent;
    }

    public Float getTimeAboveRangePercent() {
        return timeAboveRangePercent;
    }

    public void setTimeAboveRangePercent(Float timeAboveRangePercent) {
        this.timeAboveRangePercent = timeAboveRangePercent;
    }

    public Integer getTotalCarbs() {
        return totalCarbs;
    }

    public void setTotalCarbs(Integer totalCarbs) {
        this.totalCarbs = totalCarbs;
    }

    public Float getTotalInsulin() {
        return totalInsulin;
    }

    public void setTotalInsulin(Float totalInsulin) {
        this.totalInsulin = totalInsulin;
    }

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

}