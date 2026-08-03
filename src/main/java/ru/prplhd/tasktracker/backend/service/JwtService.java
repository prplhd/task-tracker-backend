package ru.prplhd.tasktracker.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final Duration accessTokenTtl;
    private final String issuer;

    public JwtService(JwtEncoder jwtEncoder,
                      @Value("${jwt.access-token-ttl}") Duration accessTokenTtl,
                      @Value("${spring.application.name}") String issuer
    ) {
        this.jwtEncoder = jwtEncoder;
        this.accessTokenTtl = accessTokenTtl;
        this.issuer = issuer;
    }

    public String generateAccessToken(Long userId) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(accessTokenTtl);

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .subject(userId.toString())
                .issuer(issuer)
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .build();

        return jwtEncoder
                .encode(JwtEncoderParameters.from(claimsSet))
                .getTokenValue();
    }
}
