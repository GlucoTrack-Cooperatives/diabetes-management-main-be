package dm.diabetesmanagementmainbe.dtos;

import dm.diabetesmanagementmainbe.enums.FoodInputMethod;
import dm.diabetesmanagementmainbe.enums.MealType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodLogDTO {
    private UUID id;
    private LocalDateTime timestamp;
    private MealType mealType;
    private Integer carbsGrams;
    private Integer calories;
    private String foodName;
    private String imageUrl;
    private FoodInputMethod inputMethod;
    private LocalDateTime createdAt;
}
