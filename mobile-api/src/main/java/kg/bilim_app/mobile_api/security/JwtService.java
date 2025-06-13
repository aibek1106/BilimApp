package kg.bilim_app.mobile_api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class JwtService {

    private final Algorithm algorithm;
    private final long expirationSeconds;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.expiration:3600}") long expirationSeconds) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.expirationSeconds = expirationSeconds;
    }

    public String generateToken(Long telegramId) {
        return JWT.create()
                .withSubject(String.valueOf(telegramId))
                .withExpiresAt(Date.from(Instant.now().plusSeconds(expirationSeconds)))
                .sign(algorithm);
    }

    public Long verifyToken(String token) throws JWTVerificationException {
        return Long.valueOf(JWT.require(algorithm).build()
                .verify(token)
                .getSubject());
    }
}
