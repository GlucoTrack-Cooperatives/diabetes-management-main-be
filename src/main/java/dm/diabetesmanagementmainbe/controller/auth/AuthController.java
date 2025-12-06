package dm.diabetesmanagementmainbe.controller.auth;

import dm.diabetesmanagementmainbe.config.security.jwt.JWTFilter;
import dm.diabetesmanagementmainbe.controller.auth.dto.AuthRequest;
import dm.diabetesmanagementmainbe.controller.auth.dto.AuthToken;
import dm.diabetesmanagementmainbe.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<AuthToken> authorize(@RequestBody AuthRequest request) {
        log.info("Authorizing request for: {}", request.getEmail());
        var token = authService.authorize(request);

        var httpHeaders = new HttpHeaders();
        String BEARER = "Bearer ";
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, BEARER + token);

        return new ResponseEntity<>(
                AuthToken.builder().jwt(token).build(),
                httpHeaders,
                HttpStatus.OK);
    }
}
