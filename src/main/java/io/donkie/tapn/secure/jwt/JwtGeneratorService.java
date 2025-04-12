package io.donkie.tapn.secure.jwt;

import io.donkie.tapn.secure.models.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public interface JwtGeneratorService {

    String generateToken(
            String Subject,
            Map<String, Object> claims,
            Date issuedTime,
            Date expirationTime,
            SignatureAlgorithm algorithm);
}
