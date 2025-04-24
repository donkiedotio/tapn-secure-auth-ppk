package io.donkie.tapn.secure.core;

import io.donkie.tapn.secure.models.DeliveryMode;
import io.donkie.tapn.secure.models.SignatureAlgorithm;
import io.donkie.tapn.secure.models.TokenType;

import java.time.Duration;
import java.util.Map;

final class TokenIssuerAware {

    /**
     * The entity (e.g., domain or URI) issuing tokens.
     */
    private final String issuer;

    /**
     * The algorithm used to sign tokens, for example, RS256.
     */
    private final SignatureAlgorithm algorithm;

    /**
     * Holds validity durations for each type of token (e.g., ACCESS, REFRESH).
     */
    private final Map<TokenType, Duration> tokenValidityMap;

    /**
     * Holds "not-before" (nbf) durations for each type of token.
     * This can be used to delay token validity until a certain time has elapsed.
     */
    private final Map<TokenType, Duration> nbfMap;

    /**
     * Defines how tokens will be delivered to the client, for instance, as raw text
     * or via cookie headers.
     */
    private final DeliveryMode deliveryMode;

    public String getIssuer() {
        return issuer;
    }

    public SignatureAlgorithm getAlgorithm() {
        return algorithm;
    }

    public Map<TokenType, Duration> getTokenValidityMap() {
        return tokenValidityMap;
    }

    public Map<TokenType, Duration> getNbfMap() {
        return nbfMap;
    }

    public DeliveryMode getDeliveryMode() {
        return deliveryMode;
    }

    private TokenIssuerAware(
            String issuer,
            SignatureAlgorithm algorithm,
            Map<TokenType, Duration> tokenValidityMap,
            Map<TokenType, Duration> nbfMap,
            DeliveryMode deliveryMode) {
        this.issuer = issuer;
        this.algorithm = algorithm;
        this.tokenValidityMap = tokenValidityMap;
        this.nbfMap = nbfMap;
        this.deliveryMode = deliveryMode;
    }

    static TokenIssuerAwareBuilder builder() {
        return new TokenIssuerAwareBuilder();
    }

    static class TokenIssuerAwareBuilder {
        private String issuer;
        private SignatureAlgorithm algorithm;
        private Map<TokenType, Duration> tokenValidityMap;
        private Map<TokenType, Duration> nbfMap;
        private DeliveryMode deliveryMode;

        TokenIssuerAwareBuilder issuer(String issuer) {
            this.issuer = issuer;
            return this;
        }

        TokenIssuerAwareBuilder algorithm(SignatureAlgorithm algorithm) {
            this.algorithm = algorithm;
            return this;
        }

        TokenIssuerAwareBuilder tokenValidityMap(Map<TokenType, Duration> tokenValidityMap) {
            this.tokenValidityMap = tokenValidityMap;
            return this;
        }

        TokenIssuerAwareBuilder nbfMap(Map<TokenType, Duration> nbfMap) {
            this.nbfMap = nbfMap;
            return this;
        }

        TokenIssuerAwareBuilder deliveryMode(DeliveryMode deliveryMode) {
            this.deliveryMode = deliveryMode;
            return this;
        }

        TokenIssuerAware build() {
            return new TokenIssuerAware(
                    issuer,
                    algorithm,
                    tokenValidityMap,
                    nbfMap,
                    deliveryMode
            );
        }
    }
}
