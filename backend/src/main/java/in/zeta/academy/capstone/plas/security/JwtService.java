package in.zeta.academy.capstone.plas.security;

import in.zeta.academy.capstone.plas.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final SecretKey key = Keys.hmacShaKeyFor(
            Decoders.BASE64.decode("kTZ6Eo8U0N7Z+pVjb9QoPU3pJZAMM9LHYEXUQXpZpHE=")
    );

    public String generateToken(Users user) {
        return Jwts.builder()
                .setSubject(user.getEmail()) // subject = email
                .claim("id", user.getId().toString()) // ✅ user ID as UUID string
                .claim("role", user.getRole().name()) // ✅ user role
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plusSeconds(86400))) // 1 day expiry
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUserMail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public UUID extractUserId(String token) {
        String idStr = extractAllClaims(token).get("id", String.class);
        return UUID.fromString(idStr);
    }

    public String extractUserRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
