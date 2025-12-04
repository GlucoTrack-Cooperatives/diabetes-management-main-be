package dm.diabetesmanagementmainbe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "\"timestamp\"", nullable = false)
    private Instant timestamp;

    @Size(max = 50)
    @NotNull
    @Column(name = "meal_type", nullable = false, length = 50)
    private String mealType;

    @NotNull
    @Column(name = "carbs_grams", nullable = false)
    private Integer carbsGrams;

    @NotNull
    @Column(name = "calories", nullable = false)
    private Integer calories;

    @Size(max = 255)
    @NotNull
    @Column(name = "food_name", nullable = false)
    private String foodName;

    @Size(max = 255)
    @Column(name = "image_url")
    private String imageUrl;

    @Size(max = 50)
    @NotNull
    @Column(name = "input_method", nullable = false, length = 50)
    private String inputMethod;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

}