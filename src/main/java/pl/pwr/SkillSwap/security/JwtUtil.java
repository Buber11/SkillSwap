package pl.pwr.SkillSwap.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final long expiration;
    private final SecretKey key;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration}") long expiration) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }


    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Możesz pozostawić tę metodę (dla kompatybilności) lub przekierować ją do nowej wersji
    public String generateToken(String username) {
        // Domyślnie ustawiamy "USER", ale w loginie już użyjemy wersji z rolą.
        return generateToken(username, "USER");
    }

    

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && isTokenValidCheck(token);
    }

    public boolean isTokenValidCheck(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            Date expiration = claimsJws.getBody().getExpiration();
            return !expiration.before(new Date());
        } catch (JwtException e) {
            return false;
        }
    }

    public boolean shouldRefreshToken(String token, long refreshThresholdMs) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            Date expiration = claimsJws.getBody().getExpiration();
            long remainingTime = expiration.getTime() - System.currentTimeMillis();
            return remainingTime < refreshThresholdMs;
        } catch (JwtException e) {
            return false;
        }
    }

}
