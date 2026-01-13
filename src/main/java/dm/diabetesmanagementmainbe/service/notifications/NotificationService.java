package dm.diabetesmanagementmainbe.service.notifications;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import dm.diabetesmanagementmainbe.dao.model.communication.FcmToken;
import dm.diabetesmanagementmainbe.dao.repository.communication.FcmTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final FirebaseMessaging firebaseMessaging;
    private final FcmTokenRepository fcmTokenRepository;


    public void sendPushToUser(UUID userId, String title, String body) {
        List<FcmToken> userTokens = fcmTokenRepository.findByUserId(userId);

        if (userTokens.isEmpty()) {
            log.warn("No FCM tokens found for user: {}", userId);
            return;
        }

        log.info("Sending push notification to {} devices for user: {}", userTokens.size(), userId);

        for (FcmToken fcmToken : userTokens) {
            try {
                sendPushToToken(fcmToken.getToken(), title, body);
            } catch (Exception e) {
                log.error("Failed to send push notification to token: {} for user: {}",
                         fcmToken.getToken(), userId, e);
            }
        }
    }

    public void sendPushToToken(String token, String title, String body) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .build();

        String response = firebaseMessaging.send(message);
        log.info("Successfully sent message to token: {} - Response: {}", token, response);
    }
}