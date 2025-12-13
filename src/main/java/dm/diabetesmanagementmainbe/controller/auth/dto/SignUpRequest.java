package dm.diabetesmanagementmainbe.controller.auth.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotNull(message = "Full name cannot be null")
    @NotBlank(message = "Full name cannot be empty")
    @Size(min = 2, max = 50, message = "Full name must be 2-50 characters")
    @Pattern(
            regexp = "^[\\p{L} .'-]+$",
            message = "Full name can only contain letters, spaces, dots, apostrophes, and hyphens"
    )
    private String fullName;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email address")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@(.+)$",
            message = "Email format is invalid"
    )
    private String email;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 1, max = 50, message = "Password must be 1-50 characters")
    private String password;
}
