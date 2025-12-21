package dm.diabetesmanagementmainbe.controller.patient.dto.log;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FoodLogRequest {

    @NotNull
    private Integer carbs;

    private Integer calories;

    private String description;

    private String imageUrl;
}
