package dm.diabetesmanagementmainbe.dao.model.logging;

import dm.diabetesmanagementmainbe.dao.model.user.Patient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "food_log")
public class FoodLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NotNull
    @Column(name = "\"timestamp\"", nullable = false)
    private Instant timestamp;

    @Size(max = 50)
    @Column(name = "meal_type", length = 50)
    private String mealType;

    @NotNull
    @Column(name = "carbs", nullable = false)
    private Integer carbs;

    @Column(name = "calories")
    private Integer calories;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    private void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

}
