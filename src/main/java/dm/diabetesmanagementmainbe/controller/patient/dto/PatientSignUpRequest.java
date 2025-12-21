package dm.diabetesmanagementmainbe.controller.patient.dto;

import dm.diabetesmanagementmainbe.controller.auth.dto.SignUpRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class PatientSignUpRequest extends SignUpRequest {

    @Size(max = 255)
    @NotNull
    private String phoneNumber;

    @NotNull
    private LocalDate dob;

    @NotNull
    private LocalDate diagnosisDate; // the date when the patient was medically diagnosed with diabetes.

    @Size(max = 255)
    @NotNull
    private String emergencyContactPhone;
}
