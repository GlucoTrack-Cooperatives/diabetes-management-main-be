package dm.diabetesmanagementmainbe.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "activity_logs", indexes = {
    @Index(name = "idx_patient_id", columnList = "patient_id"),
    @Index(name = "idx_date", columnList = "date")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(nullable = false)
    @Builder.Default
    private Integer stepCount = 0;
    
    @Column(nullable = false)
    @Builder.Default
    private Float activeEnergyBurned = 0.0f;
    
    @Column(nullable = false)
    @Builder.Default
    private Integer exerciseMinutes = 0;
    
    @Column(nullable = false)
    @Builder.Default
    private Integer sleepDurationMinutes = 0;
    
    @Column(name = "sleep_start_time")
    private LocalDateTime sleepStartTime;
    
    @Column(name = "sleep_end_time")
    private LocalDateTime sleepEndTime;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
