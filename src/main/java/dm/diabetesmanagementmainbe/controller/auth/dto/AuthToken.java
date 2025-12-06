package dm.diabetesmanagementmainbe.controller.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthToken {
    private String jwt;
}
