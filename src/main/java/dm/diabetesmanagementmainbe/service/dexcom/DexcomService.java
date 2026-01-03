package dm.diabetesmanagementmainbe.service.dexcom;

import dm.diabetesmanagementmainbe.controller.patient.dto.DexcomAuthRequest;
import dm.diabetesmanagementmainbe.controller.patient.dto.DexcomAuthRequestToDataServer;
import dm.diabetesmanagementmainbe.dao.repository.user.UserRepository;
import dm.diabetesmanagementmainbe.service.exception.DexcomAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class DexcomService {

    private final WebClient webClient;
    private final UserRepository userRepository;

    public DexcomService(@Value("${dexcom.python.service.url}") String dexcomPythonServiceUrl,
                         UserRepository userRepository) {
        this.webClient = WebClient.builder()
                .baseUrl(dexcomPythonServiceUrl)
                .build();
        this.userRepository = userRepository;
    }

    public void authenticateDexcom(DexcomAuthRequest request) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication != null ? authentication.getName() : null;
        var user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new RuntimeException("User not found"));

        webClient.post()
                .uri("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new DexcomAuthRequestToDataServer(user.getId(), request.getDexcomEmail(), request.getDexcomPassword()))
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> {
                            var statusCode = response.statusCode();
                            return response.bodyToMono(String.class)
                                    .map(errorBody -> {
                                        log.error("Dexcom authentication failed for user id: {}. Status: {}. Error: {}",
                                                user.getId(), statusCode, errorBody);
                                        return new DexcomAuthenticationException(errorBody, statusCode);
                                    });
                        }
                )
                .toBodilessEntity()
                .block();

        log.info("Dexcom authentication successful for user: {}", user.getId());
    }
}
