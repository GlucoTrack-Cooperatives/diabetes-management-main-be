package dm.diabetesmanagementmainbe.controller.auth.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotNull(message = "First name cannot be null")
    @NotBlank(message = "First name cannot be empty")
    @Size(min = 1, max = 50)
    @Pattern(
            regexp = "^[\\p{L} .'-]+$",
            message = "First name can only contain letters, spaces, dots, apostrophes, and hyphens"
    )
    private String firstName;

    @NotNull(message = "Surname cannot be null")
    @NotBlank(message = "Surname cannot be empty")
    @Size(min = 1, max = 50)
    @Pattern(
            regexp = "^[\\p{L} .'-]+$",
            message = "Surname can only contain letters, spaces, dots, apostrophes, and hyphens"
    )
    private String surname;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email address")
    @Size(max = 100)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email format is invalid")
    private String email;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 1, max = 50)
    private String password;

}
