package com.br.sgme.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.br.sgme.config.exceptions.LoginInvalidoException;
import com.br.sgme.adapters.out.bd.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private static final String ISSUER = "sgme-api";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final int TOKEN_EXPIRATION_SECONDS = 2;
    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.of("-03:00");

    private final Algorithm algorithm;

    public TokenService(@Value("${api.security.token.secret}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String generateToken(Usuario usuario) {
        return generateTokenForSubject(usuario.getLogin());
    }

    public String validateToken(String token) {
        try {
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new LoginInvalidoException(exception.getMessage());
        }
    }

    public String extractToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return bearerToken;
    }

    public String getLogin(String token) {
        return validateToken(token);
    }

    private String generateTokenForSubject(String subject) {
        try {
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(subject)
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new LoginInvalidoException(exception.getMessage());
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusDays(TOKEN_EXPIRATION_SECONDS).toInstant(ZONE_OFFSET);
    }
}