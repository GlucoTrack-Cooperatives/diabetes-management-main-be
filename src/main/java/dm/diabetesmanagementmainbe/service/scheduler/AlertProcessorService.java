package dm.diabetesmanagementmainbe.service.scheduler;

import dm.diabetesmanagementmainbe.dao.model.communication.Alert;
import dm.diabetesmanagementmainbe.dao.repository.communication.AlertRepository;
import dm.diabetesmanagementmainbe.service.notifications.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlertProcessorService {

    private final AlertRepository alertRepository;
    @Nullable
    private final NotificationService notificationService;

    /**
     * Process unacknowledged alerts and send push notifications
     * Runs every 60 seconds
     */
    @Scheduled(fixedDelay = 60000, initialDelay = 10000)
    @Transactional
    public void processNewAlerts() {
        try {
            // Find all unacknowledged alerts
            List<Alert> unacknowledgedAlerts = alertRepository.findByIsAcknowledgedFalse();

            if (unacknowledgedAlerts.isEmpty()) {
                log.debug("No new alerts to process");
                return;
            }

            log.info("Processing {} new alerts", unacknowledgedAlerts.size());

            for (Alert alert : unacknowledgedAlerts) {
                try {
                    processAlert(alert);
                } catch (Exception e) {
                    log.error("Error processing alert {}: {}", alert.getId(), e.getMessage(), e);
                }
            }

            log.info("Finished processing {} alerts", unacknowledgedAlerts.size());

        } catch (Exception e) {
            log.error("Error in alert processing job: {}", e.getMessage(), e);
        }
    }

    private void processAlert(Alert alert) {
        log.info("Processing alert: ID={}, Patient={}, Severity={}",
                alert.getId(), alert.getPatient().getId(), alert.getSeverity());

        // Send push notification if service is available
        if (notificationService != null) {
            try {
                String notificationTitle = "Glucose Alert - " + alert.getSeverity();
                String notificationBody = alert.getMessage();

                notificationService.sendPushToUser(
                        alert.getPatient().getId(),
                        notificationTitle,
                        notificationBody
                );

                log.info("Push notification sent for alert: {}", alert.getId());

            } catch (Exception e) {
                log.error("Failed to send push notification for alert {}: {}",
                        alert.getId(), e.getMessage(), e);
            }
        } else {
            log.debug("Firebase messaging is disabled. Skipping notification for alert: {}", alert.getId());
        }

        // Mark as acknowledged after processing
        // Note: You may want to add logic to auto-acknowledge or wait for user acknowledgment
        // For now, we'll keep them unacknowledged so they appear in the alert list
    }
}