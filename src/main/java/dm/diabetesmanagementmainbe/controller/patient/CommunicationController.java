package dm.diabetesmanagementmainbe.controller.patient;

import dm.diabetesmanagementmainbe.config.security.SecurityUser;
import dm.diabetesmanagementmainbe.controller.patient.dto.communication.AlertDTO;
import dm.diabetesmanagementmainbe.controller.patient.dto.communication.ChatMessageDTO;
import dm.diabetesmanagementmainbe.controller.patient.dto.communication.ChatThreadDTO;
import dm.diabetesmanagementmainbe.service.patient.CommunicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/communication")
@RequiredArgsConstructor
public class CommunicationController {

    private final CommunicationService communicationService;

    @GetMapping("/threads")
    public ResponseEntity<List<ChatThreadDTO>> getChatThreads(@AuthenticationPrincipal SecurityUser securityUser) {
        if (securityUser == null) {
            return ResponseEntity.status(401).build();
        }
        // Assuming the user is a patient for now, or we can check roles
        // But the service method is currently named findChatThreads(patientId)
        // We might need to adapt it if physicians also use this endpoint.
        // For now, let's assume this endpoint is used by patients as per the path structure in the prompt.
        // However, the prompt mentioned "/communication/threads" in the frontend code.
        // If the user is a physician, we might need a different service call or the service needs to handle both.
        
        // Let's check the role or just try to fetch threads for the user ID.
        // Since the service method is `findChatThreads(UUID patientId)`, it implies it's for patients.
        // If we want to support physicians, we need to update the service.


        return ResponseEntity.ok(communicationService.findChatThreads(securityUser.getUserId()));
    }

    @GetMapping("/threads/{threadId}/messages")
    public ResponseEntity<List<ChatMessageDTO>> getChatMessages(@PathVariable UUID threadId) {
        return ResponseEntity.ok(communicationService.findChatMessages(threadId));
    }

    @PostMapping("/threads/{threadId}/messages")
    public ResponseEntity<ChatMessageDTO> sendMessage(
            @PathVariable UUID threadId,
            @AuthenticationPrincipal SecurityUser securityUser,
            @RequestBody ChatMessageDTO messageDTO
    ) {
        if (securityUser == null) return ResponseEntity.status(401).build();
        messageDTO.setSenderId(securityUser.getUserId());
        return ResponseEntity.ok(communicationService.saveMessage(threadId, messageDTO));
    }

    @GetMapping("/alerts")
    public ResponseEntity<List<AlertDTO>> getActiveAlerts(@AuthenticationPrincipal SecurityUser securityUser) {
         if (securityUser == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(communicationService.findActiveAlerts(securityUser.getUserId()));
    }
}
