package dm.diabetesmanagementmainbe.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "patient_clinical_settings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientClinicalSettings {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private Integer targetRangeLow;
    
    @Column(nullable = false)
    private Integer targetRangeHigh;
    
    @Column(nullable = false)
    private Float insulinCarbRatio;
    
    @Column(nullable = false)
    private Float correctionFactor;
    
    @Column(nullable = false)
    private Float basalRateDaily;
    
    @Column(nullable = false)
    @Builder.Default
    private Integer hypoThresholdMgDl = 70;
    
    @Column(nullable = false)
    @Builder.Default
    private Integer hyperThresholdMgDl = 180;
    
    @Column(nullable = false)
    @Builder.Default
    private Float sickDayFactor = 1.0f;
    
    @Column(nullable = false)
    @Builder.Default
    private Float exerciseFactor = 0.8f;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
