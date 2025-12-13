package dm.diabetesmanagementmainbe.controller.auth;

import dm.diabetesmanagementmainbe.controller.auth.dto.AuthRequest;
import dm.diabetesmanagementmainbe.controller.auth.dto.LoginResponse;
import dm.diabetesmanagementmainbe.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthRequest request) {
        var token = authService.authorize(request);
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
