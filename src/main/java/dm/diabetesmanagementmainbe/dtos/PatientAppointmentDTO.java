package dm.diabetesmanagementmainbe.dtos;

import dm.diabetesmanagementmainbe.dao.model.user.AppointmentType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PatientAppointmentDTO {
    private UUID id;
    private UUID patientId;
    private String type; // Using label for frontend compatibility
    private LocalDateTime appointmentDate;
    private LocalDateTime nextDueDate;
    private String notes;
}
