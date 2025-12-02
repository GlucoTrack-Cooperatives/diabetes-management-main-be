package dm.diabetesmanagementmainbe.model;

import dm.diabetesmanagementmainbe.enums.FoodInputMethod;
import dm.diabetesmanagementmainbe.enums.MealType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "food_logs", indexes = {
    @Index(name = "idx_patient_id", columnList = "patient_id"),
    @Index(name = "idx_timestamp", columnList = "timestamp")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealType mealType;
    
    @Column(nullable = false)
    private Integer carbsGrams;
    
    @Column(nullable = false)
    private Integer calories;
    
    @Column(nullable = false)
    private String foodName;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FoodInputMethod inputMethod;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
