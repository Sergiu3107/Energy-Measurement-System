package ro.tuc.ds2024.services.utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Getter
    @Value("${secret.key}")
    private String secretKey;

    private String generateToken(String username, String role, String id) {
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .claim("id", id)
                .setIssuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    public String generateAccessToken(String username, String role, String id) {
        return generateToken(username, role, id);
    }
}
