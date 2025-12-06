package dm.diabetesmanagementmainbe.service.auth;

import dm.diabetesmanagementmainbe.config.security.jwt.TokenProvider;
import dm.diabetesmanagementmainbe.controller.auth.dto.AuthRequest;
import dm.diabetesmanagementmainbe.dao.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    public String authorize(AuthRequest request) {
        log.info("Authorizing user: {}", request.getEmail());

        if (request.getEmail() == null || request.getEmail().isBlank() ||
                request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("Email and password are required");
        }

        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
            var authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));



            return tokenProvider.createToken(authentication, user.getId(), user.getEmail(), user.getFullName());

        } catch (IllegalArgumentException e) {
            log.warn("Authentication failed for user {}: {}", request.getEmail(), e.getMessage());
            throw new IllegalArgumentException("Invalid email or password");
        }
    }

}