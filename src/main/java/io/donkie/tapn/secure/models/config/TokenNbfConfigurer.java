package io.donkie.tapn.secure.models.config;

import io.donkie.tapn.secure.models.TokenType;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class TokenNbfConfigurer {
    Map<TokenType, Duration> nbfMap = new HashMap<>();

    public Group tokenType(TokenType type) {
        return new Group(this, type);
    }

    Map<TokenType, Duration> setup() {
        return nbfMap;
    }

    public static class Group {
        private final TokenType type;
        private final TokenNbfConfigurer configurer;

        public Group(TokenNbfConfigurer configurer, TokenType type) {
            this.type = type;
            this.configurer = configurer;
        }

        public TokenNbfConfigurer withNbf(Duration nbf) {
            configurer.nbfMap.put(type, nbf);
            return configurer;
        }
    }
}
