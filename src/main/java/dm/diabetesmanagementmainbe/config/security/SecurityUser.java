package dm.diabetesmanagementmainbe.config.security;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Getter
@Setter
public class SecurityUser extends User {

    private UUID userId;
    private String userEmail;
    private String name;

    public SecurityUser(String username,
                        String password,
                        Collection<? extends GrantedAuthority> authorities,
                        UUID userId,
                        String userEmail,
                        String userName) {
        super(username, password, authorities);
        this.userId = userId;
        this.userEmail = userEmail;
        this.name = userName;
    }

    public SecurityUser(dm.diabetesmanagementmainbe.dao.model.user.User user) {
        this(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole())),
                user.getId(),
                user.getEmail(),
                user.getFirstName() + " " + user.getSurname() // userFullname
        );
    }

}

