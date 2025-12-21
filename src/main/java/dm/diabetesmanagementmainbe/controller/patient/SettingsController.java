package dm.diabetesmanagementmainbe.controller.patient;

import dm.diabetesmanagementmainbe.controller.patient.dto.settings.PatientSettingsDTO;
import dm.diabetesmanagementmainbe.service.patient.SettingsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/patients/{patientId}/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final SettingsService settingsService;

    @GetMapping
    public ResponseEntity<PatientSettingsDTO> getPatientSettings(@PathVariable UUID patientId) {
        return ResponseEntity.ok(settingsService.getPatientSettings(patientId));
    }

    @PutMapping
    public ResponseEntity<Void> updatePatientSettings(@PathVariable UUID patientId, @RequestBody @Valid PatientSettingsDTO request) {
        settingsService.updatePatientSettings(patientId, request);
        return ResponseEntity.ok().build();
    }
}
