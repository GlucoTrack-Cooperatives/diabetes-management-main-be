package dm.diabetesmanagementmainbe.controller.patient;

import dm.diabetesmanagementmainbe.controller.patient.dto.DexcomAuthRequest;
import dm.diabetesmanagementmainbe.service.dexcom.DexcomService;
import dm.diabetesmanagementmainbe.service.exception.DexcomAuthenticationException;
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
@RequestMapping("/api/dexcom")
@RequiredArgsConstructor
@Slf4j
public class DexcomAuthController {

    private final DexcomService dexcomService;

    @PostMapping("/auth")
    public ResponseEntity<Void> dexcomAuth(@RequestBody @Valid DexcomAuthRequest request) {
        try {
            dexcomService.authenticateDexcom(request);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (DexcomAuthenticationException e) {
            return ResponseEntity.status(e.getHttpStatus()).build();
        }
    }
}
