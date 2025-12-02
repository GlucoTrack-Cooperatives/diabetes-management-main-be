package dm.diabetesmanagementmainbe.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "daily_patient_stats", indexes = {
    @Index(name = "idx_patient_id", columnList = "patient_id"),
    @Index(name = "idx_date", columnList = "date"),
    @Index(name = "idx_patient_date", columnList = "patient_id,date")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyPatientStats {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(nullable = false)
    private Float averageGlucose;
    
    @Column(nullable = false)
    private Float timeInRangePercent;
    
    @Column(nullable = false)
    private Float timeBelowRangePercent;
    
    @Column(nullable = false)
    @Builder.Default
    private Float timeAboveRangePercent = 0.0f;
    
    @Column(nullable = false)
    @Builder.Default
    private Integer totalCarbs = 0;
    
    @Column(nullable = false)
    @Builder.Default
    private Float totalInsulin = 0.0f;
    
    @Column(nullable = false)
    @Builder.Default
    private Integer riskScore = 0;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
