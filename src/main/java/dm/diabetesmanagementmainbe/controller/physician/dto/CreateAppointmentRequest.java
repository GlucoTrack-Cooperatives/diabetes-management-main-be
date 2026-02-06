package dm.diabetesmanagementmainbe.controller.physician.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateAppointmentRequest {
    @NotNull
    private String type;
    @NotNull
    private LocalDateTime appointmentDate;
    private String notes;
}
