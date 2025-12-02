package dm.diabetesmanagementmainbe.model;

import dm.diabetesmanagementmainbe.enums.GlucoseSource;
import dm.diabetesmanagementmainbe.enums.GlucoseTrend;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "glucose_readings", indexes = {
    @Index(name = "idx_patient_id", columnList = "patient_id"),
    @Index(name = "idx_timestamp", columnList = "timestamp"),
    @Index(name = "idx_patient_timestamp", columnList = "patient_id,timestamp")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GlucoseReading {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    @Column(nullable = false)
    private Integer value;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GlucoseTrend trend;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GlucoseSource source;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
