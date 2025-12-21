package dm.diabetesmanagementmainbe.controller.patient;

import dm.diabetesmanagementmainbe.controller.patient.dto.lifestyle.HealthEventRequest;
import dm.diabetesmanagementmainbe.controller.patient.dto.lifestyle.LifestyleSummaryDTO;
import dm.diabetesmanagementmainbe.controller.patient.dto.lifestyle.WaterLogRequest;
import dm.diabetesmanagementmainbe.controller.patient.dto.lifestyle.WeightLogRequest;
import dm.diabetesmanagementmainbe.service.patient.LifestyleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/patients/{patientId}/lifestyle")
@RequiredArgsConstructor
public class LifestyleController {

    private final LifestyleService lifestyleService;

    @GetMapping("/summary")
    public ResponseEntity<LifestyleSummaryDTO> getLifestyleSummary(@PathVariable UUID patientId) {
        return ResponseEntity.ok(lifestyleService.getLifestyleSummary(patientId));
    }

    @PostMapping("/water")
    public ResponseEntity<Void> addWater(@PathVariable UUID patientId, @RequestBody @Valid WaterLogRequest request) {
        lifestyleService.addWater(patientId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/weight")
    public ResponseEntity<Void> logWeight(@PathVariable UUID patientId, @RequestBody @Valid WeightLogRequest request) {
        lifestyleService.logWeight(patientId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/event")
    public ResponseEntity<Void> logHealthEvent(@PathVariable UUID patientId, @RequestBody @Valid HealthEventRequest request) {
        lifestyleService.logHealthEvent(patientId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
