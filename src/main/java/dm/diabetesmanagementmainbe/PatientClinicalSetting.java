package dm.diabetesmanagementmainbe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "patient_clinical_setting")
public class PatientClinicalSetting {
    @Id
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

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
    @Column(name = "basal_rate_daily", nullable = false)
    private Float basalRateDaily;

    @NotNull
    @ColumnDefault("70")
    @Column(name = "hypo_threshold_mg_dl", nullable = false)
    private Integer hypoThresholdMgDl;

    @NotNull
    @ColumnDefault("180")
    @Column(name = "hyper_threshold_mg_dl", nullable = false)
    private Integer hyperThresholdMgDl;

    @NotNull
    @ColumnDefault("1.0")
    @Column(name = "sick_day_factor", nullable = false)
    private Float sickDayFactor;

    @NotNull
    @ColumnDefault("0.8")
    @Column(name = "exercise_factor", nullable = false)
    private Float exerciseFactor;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getTargetRangeLow() {
        return targetRangeLow;
    }

    public void setTargetRangeLow(Integer targetRangeLow) {
        this.targetRangeLow = targetRangeLow;
    }

    public Integer getTargetRangeHigh() {
        return targetRangeHigh;
    }

    public void setTargetRangeHigh(Integer targetRangeHigh) {
        this.targetRangeHigh = targetRangeHigh;
    }

    public Float getInsulinCarbRatio() {
        return insulinCarbRatio;
    }

    public void setInsulinCarbRatio(Float insulinCarbRatio) {
        this.insulinCarbRatio = insulinCarbRatio;
    }

    public Float getCorrectionFactor() {
        return correctionFactor;
    }

    public void setCorrectionFactor(Float correctionFactor) {
        this.correctionFactor = correctionFactor;
    }

    public Float getBasalRateDaily() {
        return basalRateDaily;
    }

    public void setBasalRateDaily(Float basalRateDaily) {
        this.basalRateDaily = basalRateDaily;
    }

    public Integer getHypoThresholdMgDl() {
        return hypoThresholdMgDl;
    }

    public void setHypoThresholdMgDl(Integer hypoThresholdMgDl) {
        this.hypoThresholdMgDl = hypoThresholdMgDl;
    }

    public Integer getHyperThresholdMgDl() {
        return hyperThresholdMgDl;
    }

    public void setHyperThresholdMgDl(Integer hyperThresholdMgDl) {
        this.hyperThresholdMgDl = hyperThresholdMgDl;
    }

    public Float getSickDayFactor() {
        return sickDayFactor;
    }

    public void setSickDayFactor(Float sickDayFactor) {
        this.sickDayFactor = sickDayFactor;
    }

    public Float getExerciseFactor() {
        return exerciseFactor;
    }

    public void setExerciseFactor(Float exerciseFactor) {
        this.exerciseFactor = exerciseFactor;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

}