package dm.diabetesmanagementmainbe.controller.patient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DexcomAuthRequest {

    @NotNull
    @Email
    @JsonProperty("dexcom_email")
    private String dexcomEmail;

    @NotNull
    @JsonProperty("dexcom_password")
    private String dexcomPassword;
}
