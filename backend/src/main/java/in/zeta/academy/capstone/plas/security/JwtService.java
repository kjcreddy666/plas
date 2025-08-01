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

@Service
public class JwtService {

    private final SecretKey key = Keys.hmacShaKeyFor(
            Decoders.BASE64.decode("kTZ6Eo8U0N7Z+pVjb9QoPU3pJZAMM9LHYEXUQXpZpHE=")
    );

    public String generateToken(Users user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plusSeconds(86400))) // 1 day
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUserMail(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}