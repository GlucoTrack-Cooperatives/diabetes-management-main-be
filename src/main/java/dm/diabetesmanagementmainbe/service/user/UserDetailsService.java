package dm.diabetesmanagementmainbe.service.user;

import dm.diabetesmanagementmainbe.config.security.SecurityUser;
import dm.diabetesmanagementmainbe.dao.model.user.User;
import dm.diabetesmanagementmainbe.dao.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userDetailsService")
@RequiredArgsConstructor
@Slf4j
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) {
        log.info("Authenticating {}", email);

        return userRepository.findByEmail(email)
                .map(this::createUser)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " was not found in the database"));
    }

    public SecurityUser createUser(User user) {
        validateIsActive(user);
        return new SecurityUser(user);
    }

    private void validateIsActive(User user) {
        if (user.getIsActive() == null || !user.getIsActive()) {
            throw new UsernameNotFoundException("User " + user.getEmail() + " is not active");
        }
    }
}
