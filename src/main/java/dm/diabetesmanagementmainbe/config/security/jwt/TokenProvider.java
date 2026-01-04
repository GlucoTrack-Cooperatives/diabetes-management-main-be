package dm.diabetesmanagementmainbe.config.security.jwt;

import dm.diabetesmanagementmainbe.config.security.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";

    private Key key;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.validity.in-seconds:3600}")
    private Long validityInSeconds;

    @Value("${jwt.validity.in-seconds-for-remember-me:86400}")
    private Long validityInSecondsForRememberMe;

    @PostConstruct
    public void init() {
        byte[] keyBytes;
        if (!StringUtils.isEmpty(secretKey)) {
            log.warn("Warning: the JWT key used is not Base64-encoded. " +
                    "We recommend using a Base64-encoded JWT secret key for optimum security.");
            keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        } else {
            log.debug("Using a Base64-encoded JWT secret key");
            keyBytes = Decoders.BASE64.decode(secretKey);
        }
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication, UUID userId, String userEmail, String userFullName) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date date = new Date();
        long now = date.getTime();
        Date validity = new Date(now + this.validityInSeconds * 2592000);


        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(date)
                .claim(AUTHORITIES_KEY, authorities)
                .claim("userId", userId)
                .claim("userEmail", userEmail)
                .claim("userFullName", userFullName)
                .claim("roles", authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.debug("Invalid JWT token: {}", e.getMessage());
        }
        return false;
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Authentication getAuthentication(String token) {
        var claims = getClaimsFromToken(token);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (claims != null && claims.containsKey(AUTHORITIES_KEY)) {
            authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        String userIdStr = claims.get("userId", String.class);
        UUID userId = userIdStr != null ? UUID.fromString(userIdStr) : null;
        String userEmail = claims.get("userEmail", String.class);
        String userFullName = claims.get("userFullName", String.class);
        String principalName = claims.getSubject(); // Usually the email or username

        SecurityUser principal = new SecurityUser(
                principalName,
                "",
                authorities,
                userId,
                userEmail,
                userFullName
        );

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

}