package io.donkie.tapn.secure.core;

import io.donkie.tapn.secure.models.DeliveryMode;
import io.donkie.tapn.secure.models.SignatureAlgorithm;
import io.donkie.tapn.secure.models.TokenType;

import java.time.Duration;
import java.util.Map;

public final class TokenDefaults {
    public static final String ID = "defaultTokenIssuer";
    public static final String ISSUER = null;
    public static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.RS256;

    public static final Map<TokenType, Duration> TOKEN_VALIDITY_MAP = Map.of(
            TokenType.ACCESS, Duration.ofHours(1),
            TokenType.REFRESH, Duration.ofDays(15)
    );

    public static final Map<TokenType, Duration> NBF_MAP = Map.of(
            TokenType.ACCESS, Duration.ofSeconds(0),
            TokenType.REFRESH, Duration.ofSeconds(0)
    );

    public static final DeliveryMode DELIVERY_MODE = DeliveryMode.COOKIES;

    private TokenDefaults() {
        // Prevent instantiation
    }
}

