package dm.diabetesmanagementmainbe.model;

import dm.diabetesmanagementmainbe.enums.HealthEventCategory;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "health_events", indexes = {
    @Index(name = "idx_patient_id", columnList = "patient_id"),
    @Index(name = "idx_timestamp", columnList = "timestamp")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthEvent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HealthEventCategory category;
    
    @Column(nullable = false)
    @Builder.Default
    private Integer severity = 3;
    
    @Column(columnDefinition = "TEXT")
    private String note;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
