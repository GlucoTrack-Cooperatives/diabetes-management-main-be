package dm.diabetesmanagementmainbe.controller.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String jwt;
    private String role;
    private UUID userId;
}
