package io.donkie.tapn.secure.models.config;

import io.donkie.tapn.secure.models.DeliveryMode;
import io.donkie.tapn.secure.models.SignatureAlgorithm;
import io.donkie.tapn.secure.models.TokenType;
import lombok.*;

import java.time.Duration;
import java.util.Map;
import java.util.function.UnaryOperator;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenIssuerDraft {
    // can be used as bean name when there are more than one TokenIssuer Configured
    private String id;
    private String issuer;
    private SignatureAlgorithm algorithm;
    private Map<TokenType, Duration> tokenValidityMap;
    private Map<TokenType, Duration> nbfMap;
    private DeliveryMode deliveryMode;

    public static DraftBuilder draft() {
        return new DraftBuilder();
    }

    public static class DraftBuilder {
        private String id;
        private String issuer;
        private SignatureAlgorithm algorithm;
        private Map<TokenType, Duration> tokenValidityMap;
        private Map<TokenType, Duration> nbfMap;
        private DeliveryMode deliveryMode;

        public DraftBuilder withId(String qualifiedIssuerName) {
            this.id = qualifiedIssuerName;
            return this;
        }

        public DraftBuilder withIssuer(String issuer) {
            this.issuer = issuer;
            return this;
        }

        public DraftBuilder withAlgorithm(SignatureAlgorithm algorithm) {
            this.algorithm = algorithm;
            return this;
        }

        public DraftBuilder withTokensAndValidity(UnaryOperator<TokenValidityConfigurer> configurerUnaryOperator) {
            this.tokenValidityMap = configurerUnaryOperator.apply(new TokenValidityConfigurer()).setup();
            return this;
        }

        public DraftBuilder withTokensAndValidity(Map<TokenType, Duration> validityMap) {
            this.tokenValidityMap = validityMap;
            return this;
        }

        public DraftBuilder withTokensAndNbf(UnaryOperator<TokenNbfConfigurer> configurerUnaryOperator) {
            this.nbfMap = configurerUnaryOperator.apply(new TokenNbfConfigurer()).setup();
            return this;
        }

        public DraftBuilder withTokensAndNbf(Map<TokenType, Duration> nbfMap) {
            this.nbfMap = nbfMap;
            return this;
        }

        public DraftBuilder withDeliveryMode(DeliveryMode deliveryMode) {
            this.deliveryMode = deliveryMode;
            return this;
        }

        public TokenIssuerDraft build() {
            return new TokenIssuerDraft(
                    id,
                    issuer, algorithm,
                    tokenValidityMap,
                    nbfMap,
                    deliveryMode
            );
        }
    }

}
