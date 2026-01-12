package dm.diabetesmanagementmainbe.controller.notifications;

import dm.diabetesmanagementmainbe.config.security.SecurityUser;
import dm.diabetesmanagementmainbe.dao.model.communication.FcmToken;
import dm.diabetesmanagementmainbe.service.notifications.FcmTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fcm-tokens")
@RequiredArgsConstructor
@Slf4j
public class FcmTokenController {

    private final FcmTokenService fcmTokenService;

    @PostMapping("/register")
    public ResponseEntity<FcmToken> registerToken(
            @RequestBody TokenRegistrationRequest request,
            Authentication authentication) {

        UUID userId = getUserIdFromAuthentication(authentication);
        log.info("Registering FCM token for user: {}", userId);

        FcmToken fcmToken = fcmTokenService.registerToken(userId, request.token());

        return ResponseEntity.ok(fcmToken);
    }

    @DeleteMapping("/unregister")
    public ResponseEntity<Void> unregisterToken(@RequestBody TokenRegistrationRequest request) {
        log.info("Unregistering FCM token");
        fcmTokenService.unregisterToken(request.token());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<FcmToken>> getUserTokens(Authentication authentication) {
        UUID userId = getUserIdFromAuthentication(authentication);
        List<FcmToken> tokens = fcmTokenService.getUserTokens(userId);
        return ResponseEntity.ok(tokens);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> unregisterAllTokens(Authentication authentication) {
        UUID userId = getUserIdFromAuthentication(authentication);
        log.info("Unregistering all FCM tokens for user: {}", userId);
        fcmTokenService.unregisterAllUserTokens(userId);
        return ResponseEntity.ok().build();
    }

    private UUID getUserIdFromAuthentication(Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        return securityUser.getUserId();
    }

    public record TokenRegistrationRequest(String token) {}
}