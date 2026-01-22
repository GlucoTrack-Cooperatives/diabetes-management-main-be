package dm.diabetesmanagementmainbe.service.notifications;

import com.google.firebase.messaging.*;
import dm.diabetesmanagementmainbe.dao.model.communication.FcmToken;
import dm.diabetesmanagementmainbe.dao.repository.communication.FcmTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final FirebaseMessaging firebaseMessaging;
    private final FcmTokenRepository fcmTokenRepository;

    /**
     * Sends a simple notification (Title + Body) without data payload.
     * Keeps backward compatibility for existing calls.
     */
    public void sendPushToUser(UUID userId, String title, String body) {
        sendPushToUser(userId, title, body, null);
    }

    /**
     * Sends a notification with a data payload.
     * The 'data' map is essential for the frontend to distinguish between
     * 'chat' and 'glucose' alerts (using the "type" key).
     */
    public void sendPushToUser(UUID userId, String title, String body, Map<String, String> data) {
        List<FcmToken> userTokens = fcmTokenRepository.findByUserId(userId);

        if (userTokens.isEmpty()) {
            log.warn("No FCM tokens found for user: {}", userId);
            return;
        }

        log.info("Sending push notification to {} devices for user: {}", userTokens.size(), userId);

        for (FcmToken fcmToken : userTokens) {
            try {
                sendPushToToken(fcmToken.getToken(), title, body, data);
            } catch (Exception e) {
                log.error("Failed to send push notification to token: {} for user: {}",
                        fcmToken.getToken(), userId, e);
            }
        }
    }

    private void sendPushToToken(String token, String title, String body, Map<String, String> data) throws FirebaseMessagingException {
        // Build the basic notification structure
        Message.Builder messageBuilder = Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build());

        // Attach data payload if provided
        // This is where we pass "type": "chat" or "type": "alert"
        if (data != null && !data.isEmpty()) {
            messageBuilder.putAllData(data);
        }

        String response = firebaseMessaging.send(messageBuilder.build());
        log.info("Successfully sent message to token: {} - Response: {}", token, response);
    }
}