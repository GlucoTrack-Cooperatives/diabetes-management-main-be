package dm.diabetesmanagementmainbe.controller.auth;

import dm.diabetesmanagementmainbe.controller.auth.dto.AuthRequest;
import dm.diabetesmanagementmainbe.controller.auth.dto.AuthToken;
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
    public ResponseEntity<AuthToken> login(@RequestBody @Valid AuthRequest request) {
        var token = authService.authorize(request);
        return ResponseEntity.ok(AuthToken.builder().jwt(token).build());
    }
}


//@RestController
//@RequestMapping("api/auth")
//@RequiredArgsConstructor
//@Slf4j
//public class AuthController {
//
//    private final AuthService authService;
//
//    @PostMapping
//    public ResponseEntity<AuthToken> authorize(@RequestBody AuthRequest request) {
//        log.info("Authorizing request for: {}", request.getEmail());
//        var token = authService.authorize(request);
//
//        var httpHeaders = new HttpHeaders();
//        String BEARER = "Bearer ";
//        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, BEARER + token);
//
//        return new ResponseEntity<>(
//                AuthToken.builder().jwt(token).build(),
//                httpHeaders,
//                HttpStatus.OK);
//    }
//}