package io.donkie.tapn.secure.jwt;

import io.donkie.tapn.secure.models.SignatureAlgorithm;

import java.time.Instant;
import java.util.Map;

public record JwtPayload(
        String subject,
        Map<String, Object> claims,
        Instant issueAt,
        Instant expiration,
        String jti,
        SignatureAlgorithm algorithm
) {
}
