package dm.diabetesmanagementmainbe.dto.pubsub;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class GlucoseAlertMessage {

    @JsonProperty("patient_id")
    private UUID patientId;

    @JsonProperty("glucose_value")
    private Double glucoseValue;

    @JsonProperty("alert_type")
    private String alertType;

    @JsonProperty("severity")
    private String severity;

    @JsonProperty("timestamp")
    private Instant timestamp;

    @JsonProperty("message")
    private String message;
}
