package dm.diabetesmanagementmainbe.controller.patient;

import dm.diabetesmanagementmainbe.controller.patient.dto.log.FoodLogRequest;
import dm.diabetesmanagementmainbe.controller.patient.dto.log.InsulinDoseRequest;
import dm.diabetesmanagementmainbe.controller.patient.dto.log.LogEntryDTO;
import dm.diabetesmanagementmainbe.service.patient.LogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients/{patientId}/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @PostMapping("/food")
    public ResponseEntity<Void> logFood(@PathVariable UUID patientId, @RequestBody @Valid FoodLogRequest request) {
        logService.createFoodLog(patientId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/insulin")
    public ResponseEntity<Void> logInsulin(@PathVariable UUID patientId, @RequestBody @Valid InsulinDoseRequest request) {
        logService.createInsulinDose(patientId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/recent")
    public ResponseEntity<List<LogEntryDTO>> getRecentLogs(@PathVariable UUID patientId) {
        return ResponseEntity.ok(logService.findRecentLogs(patientId));
    }
}
