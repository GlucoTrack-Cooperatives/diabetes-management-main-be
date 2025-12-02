package dm.diabetesmanagementmainbe.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "biometrics_log", indexes = {
    @Index(name = "idx_patient_id", columnList = "patient_id"),
    @Index(name = "idx_date", columnList = "date")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BiometricsLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(nullable = false)
    private Float weightKg;
    
    @Column(nullable = false)
    private Integer waterIntakeMl;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
