package dm.diabetesmanagementmainbe.model;

import dm.diabetesmanagementmainbe.enums.AlertType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "alerts", indexes = {
    @Index(name = "idx_patient_id", columnList = "patient_id"),
    @Index(name = "idx_timestamp", columnList = "timestamp"),
    @Index(name = "idx_is_acknowledged", columnList = "is_acknowledged")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alert {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertType type;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;
    
    @Column(name = "is_acknowledged")
    @Builder.Default
    private Boolean isAcknowledged = false;
    
    @Column(name = "acknowledged_at")
    private LocalDateTime acknowledgedAt;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
