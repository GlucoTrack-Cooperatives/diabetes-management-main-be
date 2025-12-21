package dm.diabetesmanagementmainbe.controller.patient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class DexcomAuthRequestToDataServer {

    @NotNull
    @JsonProperty("user_uuid")
    private UUID patientId;

    @NotNull
    @Email
    @JsonProperty("dexcom_email")
    private String dexcomEmail;

    @NotNull
    @JsonProperty("dexcom_password")
    private String dexcomPassword;
}
