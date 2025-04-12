package io.donkie.tapn.secure.models;

import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.util.Map;

@Getter
@Builder
public class TokenIssuerAware {

    /**
     * The entity (e.g., domain or URI) issuing tokens.
     */
    private String issuer;

    /**
     * The algorithm used to sign tokens, for example, RS256.
     */
    private SignatureAlgorithm algorithm;

    /**
     * Holds validity durations for each type of token (e.g., ACCESS, REFRESH).
     */
    private Map<TokenType, Duration> tokenValidityMap;

    /**
     * Holds "not-before" (nbf) durations for each type of token.
     * This can be used to delay token validity until a certain time has elapsed.
     */
    private Map<TokenType, Duration> nbfMap;

    /**
     * Defines how tokens will be delivered to the client, for instance, as raw text
     * or via cookie headers.
     */
    private DeliveryMode deliveryMode;
}
