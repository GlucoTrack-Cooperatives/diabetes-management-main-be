package dm.diabetesmanagementmainbe.controller.patient;

import dm.diabetesmanagementmainbe.config.security.SecurityUser;
import dm.diabetesmanagementmainbe.controller.patient.dto.settings.PatientSettingsDTO;
import dm.diabetesmanagementmainbe.service.patient.SettingsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/patients/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final SettingsService settingsService;

    @GetMapping
    public ResponseEntity<PatientSettingsDTO> getPatientSettings(
            @AuthenticationPrincipal SecurityUser user
    ) {
        return ResponseEntity.ok(settingsService.getPatientSettings(user.getUserId()));
    }

    @PutMapping
    public ResponseEntity<PatientSettingsDTO> updatePatientSettings(
            @AuthenticationPrincipal SecurityUser user,
            @RequestBody @Valid PatientSettingsDTO request
    ) {
        settingsService.updatePatientSettings(user.getUserId(), request);
        return ResponseEntity.ok(settingsService.getPatientSettings(user.getUserId()));

    }
}

