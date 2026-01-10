package dm.diabetesmanagementmainbe.controller.patient.dto.log;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Data
@Getter
@Setter
public class FoodLogRequest {

    @NotNull
    private Integer carbs;

    private Integer calories;

    private String description;

    private String imageUrl;

    private String mealType;

    private Instant timestamp;
}
