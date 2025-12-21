package dm.diabetesmanagementmainbe.controller.patient;

import dm.diabetesmanagementmainbe.controller.patient.dto.PatientSignUpRequest;
import dm.diabetesmanagementmainbe.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@Slf4j
public class PatientController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUpPatient(@RequestBody @Valid PatientSignUpRequest request) {
        userService.signUpPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
