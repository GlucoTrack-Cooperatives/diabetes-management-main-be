package dm.diabetesmanagementmainbe.model;

import dm.diabetesmanagementmainbe.enums.InsulinType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "insulin_doses", indexes = {
    @Index(name = "idx_patient_id", columnList = "patient_id"),
    @Index(name = "idx_timestamp", columnList = "timestamp")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsulinDose {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InsulinType insulinType;
    
    @Column(nullable = false)
    private Float units;
    
    @Column(name = "is_correction")
    @Builder.Default
    private Boolean isCorrection = false;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
