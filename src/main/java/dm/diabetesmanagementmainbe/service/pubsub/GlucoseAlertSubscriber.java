package dm.diabetesmanagementmainbe.service.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import dm.diabetesmanagementmainbe.dao.model.communication.Alert;
import dm.diabetesmanagementmainbe.dao.repository.communication.AlertRepository;
import dm.diabetesmanagementmainbe.dao.repository.user.PatientRepository;
import dm.diabetesmanagementmainbe.dto.pubsub.GlucoseAlertMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GlucoseAlertSubscriber {

    private final AlertRepository alertRepository;
    private final PatientRepository patientRepository;
    private final ObjectMapper objectMapper;

    @ServiceActivator(inputChannel = "glucoseAlertInputChannel")
    public void handleGlucoseAlert(Message<?> message) {
        try {
            String payload = new String((byte[]) message.getPayload());
            log.info("Received glucose alert message: {}", payload);

            GlucoseAlertMessage alertMessage = objectMapper.readValue(payload, GlucoseAlertMessage.class);

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

            BasicAcknowledgeablePubsubMessage originalMessage = message.getHeaders()
                    .get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
            if (originalMessage != null) {
                originalMessage.ack();
                log.info("Message acknowledged");
            }

        } catch (Exception e) {
            log.error("Error processing glucose alert message", e);
            BasicAcknowledgeablePubsubMessage originalMessage = message.getHeaders()
                    .get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
            if (originalMessage != null) {
                originalMessage.nack();
            }
        }
    }
}
