package dm.diabetesmanagementmainbe.controller.patient;

import dm.diabetesmanagementmainbe.controller.patient.dto.PatientSignUpRequest;
import dm.diabetesmanagementmainbe.controller.patient.dto.UpdatePatientProfileRequest;
import dm.diabetesmanagementmainbe.dtos.PatientDTO;
import dm.diabetesmanagementmainbe.service.patient.PatientService;
import dm.diabetesmanagementmainbe.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@Slf4j
public class PatientController {

    private final UserService userService;
    private final PatientService patientService;


    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUpPatient(@RequestBody @Valid PatientSignUpRequest request) {
        userService.signUpPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable UUID patientId) {
        return ResponseEntity.ok(patientService.getPatient(patientId));
    }

    @PutMapping("/{patientId}/profile")
    public ResponseEntity<Void> updatePatientProfile(@PathVariable UUID patientId, @RequestBody @Valid UpdatePatientProfileRequest request) {
        patientService.updatePatientProfile(patientId, request);
        return ResponseEntity.ok().build();
    }
}
