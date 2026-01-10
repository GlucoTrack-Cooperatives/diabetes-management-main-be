package dm.diabetesmanagementmainbe.controller.physician;

import dm.diabetesmanagementmainbe.config.security.SecurityUser;
import dm.diabetesmanagementmainbe.controller.physician.dto.InvitePatientRequest;
import dm.diabetesmanagementmainbe.controller.physician.dto.PatientOverviewDTO;
import dm.diabetesmanagementmainbe.controller.physician.dto.PhysicianSignUpRequest;
import dm.diabetesmanagementmainbe.dtos.PatientDTO;
import dm.diabetesmanagementmainbe.service.physician.PhysicianService;
import dm.diabetesmanagementmainbe.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/physicians")
@RequiredArgsConstructor
@Slf4j
public class PhysicianController {

    private final UserService userService;
    private final PhysicianService physicianService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUpPhysician(@RequestBody @Valid PhysicianSignUpRequest request) {
        userService.signUpPhysician(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/invite-patient")
    public ResponseEntity<Void> invitePatient(@AuthenticationPrincipal SecurityUser securityUser,
                                              @RequestBody @Valid InvitePatientRequest request) {
        if (securityUser == null) {
            log.warn("Unauthorized access attempt to invite-patient");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        physicianService.invitePatient(securityUser.getUserId(), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/patients")
    public ResponseEntity<List<PatientOverviewDTO>> getPatients(@AuthenticationPrincipal SecurityUser securityUser) {
        // This leverages the PatientOverviewDTO to provide exactly what the dashboard needs
        // Defensive check: If security is misconfigured or token is missing
        if (securityUser == null) {
            log.warn("Unauthorized access attempt to get patients");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(physicianService.getPatients(securityUser.getUserId()));
    }
}
