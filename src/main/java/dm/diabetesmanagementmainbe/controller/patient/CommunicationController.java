package dm.diabetesmanagementmainbe.controller.patient;

import dm.diabetesmanagementmainbe.controller.patient.dto.communication.AlertDTO;
import dm.diabetesmanagementmainbe.controller.patient.dto.communication.ChatMessageDTO;
import dm.diabetesmanagementmainbe.controller.patient.dto.communication.ChatThreadDTO;
import dm.diabetesmanagementmainbe.service.patient.CommunicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients/{patientId}/communication")
@RequiredArgsConstructor
public class CommunicationController {

    private final CommunicationService communicationService;

    @GetMapping("/threads")
    public ResponseEntity<List<ChatThreadDTO>> getChatThreads(@PathVariable UUID patientId) {
        return ResponseEntity.ok(communicationService.findChatThreads(patientId));
    }

    @GetMapping("/threads/{threadId}/messages")
    public ResponseEntity<List<ChatMessageDTO>> getChatMessages(@PathVariable UUID patientId, @PathVariable UUID threadId) {
        return ResponseEntity.ok(communicationService.findChatMessages(threadId));
    }

    @GetMapping("/alerts")
    public ResponseEntity<List<AlertDTO>> getActiveAlerts(@PathVariable UUID patientId) {
        return ResponseEntity.ok(communicationService.findActiveAlerts(patientId));
    }
}
