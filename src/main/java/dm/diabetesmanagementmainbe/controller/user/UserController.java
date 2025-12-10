package dm.diabetesmanagementmainbe.controller.user;

import dm.diabetesmanagementmainbe.controller.user.dto.UserSignUpRequest;
import dm.diabetesmanagementmainbe.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid UserSignUpRequest request) {
        userService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*@PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody @Valid CompletePasswordResetRequest request) {
        //userService.resetPassword(request);
        return ResponseEntity.ok().build();
    }*/

}
