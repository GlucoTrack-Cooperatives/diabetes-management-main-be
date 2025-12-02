package dm.diabetesmanagementmainbe.model;

import dm.diabetesmanagementmainbe.enums.MedicationFrequency;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "medications", indexes = {
    @Index(name = "idx_patient_id", columnList = "patient_id"),
    @Index(name = "idx_start_date", columnList = "start_date")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medication {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private String medicationName;
    
    @Column(nullable = false)
    private Float dosageMg;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MedicationFrequency frequency;
    
    @Column(nullable = false)
    private LocalDate startDate;
    
    @Column(name = "end_date")
    private LocalDate endDate;
    
    @Column(columnDefinition = "TEXT")
    private String sideEffects;
    
    @Column(columnDefinition = "jsonb")
    private String interactionWarnings;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
