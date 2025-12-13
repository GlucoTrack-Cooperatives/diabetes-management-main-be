package dm.diabetesmanagementmainbe.controller.physician;

import dm.diabetesmanagementmainbe.controller.physician.dto.PhysicianSignUpRequest;
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
@RequestMapping("/api/physicians")
@RequiredArgsConstructor
@Slf4j
public class PhysicianController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUpPhysician(@RequestBody @Valid PhysicianSignUpRequest request) {
        userService.signUpPhysician(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
