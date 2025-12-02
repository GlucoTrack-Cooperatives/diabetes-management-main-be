package dm.diabetesmanagementmainbe.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "patients", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_physician_id", columnList = "physician_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false)
    private String phoneNumbers;
    
    @Column(nullable = false)
    private LocalDate dob;
    
    @Column(nullable = false)
    private LocalDate diagnosisDate;
    
    @Column(nullable = false)
    private String emergencyContactPhone;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
}
