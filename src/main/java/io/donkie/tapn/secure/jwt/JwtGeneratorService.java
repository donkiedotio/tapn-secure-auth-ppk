package io.donkie.tapn.secure.jwt;

public interface JwtGeneratorService {

    String generateToken(JwtPayload payload);
}
