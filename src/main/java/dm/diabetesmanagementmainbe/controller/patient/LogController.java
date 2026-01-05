package dm.diabetesmanagementmainbe.controller.patient;

import dm.diabetesmanagementmainbe.controller.patient.dto.log.FoodLogRequest;
import dm.diabetesmanagementmainbe.controller.patient.dto.log.InsulinDoseRequest;
import dm.diabetesmanagementmainbe.controller.patient.dto.log.LogEntryDTO;
import dm.diabetesmanagementmainbe.controller.patient.dto.log.MedicationDTO;
import dm.diabetesmanagementmainbe.service.patient.FoodAnalysisService;
import dm.diabetesmanagementmainbe.dtos.FoodAnalysisResponseDTO;
import dm.diabetesmanagementmainbe.service.patient.LogService;
import dm.diabetesmanagementmainbe.service.patient.MedicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients/{patientId}/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;
    private final FoodAnalysisService foodAnalysisService;
    private final MedicationService medicationService;

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

    @GetMapping("/meals")
    public ResponseEntity<List<LogEntryDTO>> getRecentMeals(@PathVariable UUID patientId) {
        return ResponseEntity.ok(logService.findRecentMeals(patientId));
    }

    @GetMapping("/medications")
    public ResponseEntity<List<MedicationDTO>> getMedications() {
        return ResponseEntity.ok(medicationService.getAllMedications());
    }

    @PostMapping("/analyze")
    public ResponseEntity<FoodAnalysisResponseDTO> analyzeFood(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        FoodAnalysisResponseDTO result = foodAnalysisService.analyzeFoodImage(file);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.internalServerError().build();
    }
}
