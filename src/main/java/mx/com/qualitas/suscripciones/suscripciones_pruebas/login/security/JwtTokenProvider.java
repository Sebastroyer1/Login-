package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.config.ConfigService;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception.ExpiredTokenException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final ConfigService configService;

    @Value("${jwt.access-token-validity-ms:2700000}")   // 45 minutos
    private long accessTokenValidity;
    @Value("${jwt.refresh-token-validity-ms:7200000}")  // 2 horas
    private long refreshTokenValidity;

    private Key key;

    @PostConstruct
    public void init() {
        String secretBase64 = configService.getSecurityKey();
        // El String viene en Base64
        byte[] keyBytes = Decoders.BASE64.decode(secretBase64);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // --- CREACIÓN DE TOKENS ---
    public String createAccessToken(String username, String role) {
        return createToken(username, role, accessTokenValidity, null);
    }
    public String createRefreshToken(String username, String role) {
        return createToken(username, role, refreshTokenValidity, "refresh");
    }

    private String createToken(String username, String role, long validity, String type) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validity);

        JwtBuilder builder = Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS512);

        if (type != null) {
            builder.claim("type", type);
        }
        return builder.compact();
    }

    public Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            throw new ExpiredTokenException("El token ha expirado");
        } catch (Exception ex) {
            throw new InvalidTokenException("Token inválido");
        }
    }
}
