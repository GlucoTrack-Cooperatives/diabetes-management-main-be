package dm.diabetesmanagementmainbe.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "physicians", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_license", columnList = "license_number")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Physician {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false)
    private String specialty;
    
    @Column(nullable = false, unique = true)
    private String licenseNumber;
    
    @Column(nullable = false)
    private String clinicName;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
}
