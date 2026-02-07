package dm.diabetesmanagementmainbe.controller.internal;

import dm.diabetesmanagementmainbe.dao.model.communication.Alert;
import dm.diabetesmanagementmainbe.dao.repository.communication.AlertRepository;
import dm.diabetesmanagementmainbe.dao.repository.user.PatientRepository;
import dm.diabetesmanagementmainbe.dto.pubsub.GlucoseAlertMessage;
import dm.diabetesmanagementmainbe.service.notifications.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/diabetes-management/internal/alerts")
@Slf4j
@RequiredArgsConstructor
public class InternalAlertController {

    private final AlertRepository alertRepository;
    private final PatientRepository patientRepository;
    @Nullable
    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Void> receiveAlert(@RequestBody GlucoseAlertMessage alertMessage) {
        try {
            log.info("Received alert via HTTP from Python backend: patient={}, value={}, type={}",
                    alertMessage.getPatientId(), alertMessage.getGlucoseValue(), alertMessage.getAlertType());

            var patient = patientRepository.findById(alertMessage.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found: " + alertMessage.getPatientId()));

            var alert = new Alert();
            alert.setPatient(patient);
            alert.setTimestamp(alertMessage.getTimestamp());
            alert.setSeverity(alertMessage.getSeverity() != null ? alertMessage.getSeverity() : alertMessage.getAlertType());
            alert.setMessage(alertMessage.getMessage());
            alert.setIsAcknowledged(false);

            alertRepository.save(alert);

            log.info("Saved glucose alert for patient: {} with severity: {}",
                    alertMessage.getPatientId(), alert.getSeverity());

            if (notificationService != null) {
                try {
                    String notificationTitle = "Glucose Alert - " + alert.getSeverity();
                    String notificationBody = alertMessage.getMessage();

                    notificationService.sendPushToUser(patient.getId(), notificationTitle, notificationBody);
                    log.info("Push notification sent to patient: {}", alertMessage.getPatientId());
                } catch (Exception notificationError) {
                    log.error("Failed to send push notification for patient: {}",
                            alertMessage.getPatientId(), notificationError);
                }
            } else {
                log.debug("Firebase messaging is disabled. Skipping push notification for patient: {}", alertMessage.getPatientId());
            }

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("Error processing HTTP alert", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}