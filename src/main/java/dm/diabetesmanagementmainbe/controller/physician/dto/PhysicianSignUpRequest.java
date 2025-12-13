package dm.diabetesmanagementmainbe.controller.physician.dto;

import dm.diabetesmanagementmainbe.controller.auth.dto.SignUpRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PhysicianSignUpRequest extends SignUpRequest {

    @Size(max = 255)
    @NotNull
    private String specialty;

    @Size(max = 255)
    @NotNull
    private String licenseNumber;

    @Size(max = 255)
    @NotNull
    private String clinicName;
}
