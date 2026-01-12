package dm.diabetesmanagementmainbe.service.notifications;

import dm.diabetesmanagementmainbe.dao.model.communication.FcmToken;
import dm.diabetesmanagementmainbe.dao.repository.communication.FcmTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FcmTokenService {

    private final FcmTokenRepository fcmTokenRepository;

    @Transactional
    public FcmToken registerToken(UUID userId, String token) {
        var existingToken = fcmTokenRepository.findAll().stream()
                .filter(t -> t.getToken().equals(token))
                .findFirst();

        if (existingToken.isPresent()) {
            var fcmToken = existingToken.get();
            if (!fcmToken.getUserId().equals(userId)) {
                log.info("Updating token ownership from user {} to user {}", fcmToken.getUserId(), userId);
                fcmToken.setUserId(userId);
            }
            fcmTokenRepository.save(fcmToken);
            log.info("Updated existing FCM token for user: {}", userId);
            return fcmToken;
        } else {
            var fcmToken = new FcmToken();
            fcmToken.setUserId(userId);
            fcmToken.setToken(token);
            fcmTokenRepository.save(fcmToken);
            log.info("Registered new FCM token for user: {}", userId);
            return fcmToken;
        }
    }

    @Transactional
    public void unregisterToken(String token) {
        fcmTokenRepository.findAll().stream()
                .filter(t -> t.getToken().equals(token))
                .findFirst()
                .ifPresent(fcmToken -> {
                    fcmTokenRepository.delete(fcmToken);
                    log.info("Unregistered FCM token for user: {}", fcmToken.getUserId());
                });
    }

    public List<FcmToken> getUserTokens(UUID userId) {
        return fcmTokenRepository.findByUserId(userId);
    }

    @Transactional
    public void unregisterAllUserTokens(UUID userId) {
        List<FcmToken> userTokens = fcmTokenRepository.findByUserId(userId);
        fcmTokenRepository.deleteAll(userTokens);
        log.info("Unregistered {} FCM tokens for user: {}", userTokens.size(), userId);
    }
}