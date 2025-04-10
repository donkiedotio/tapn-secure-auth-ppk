package io.donkie.tapn.secure.models.config;

import io.donkie.tapn.secure.models.TokenType;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class TokenValidityConfigurer {
    Map<TokenType, Duration> validityMap = new HashMap<>();

    public Group tokenType(TokenType type) {
        return new Group(this, type);
    }

    Map<TokenType, Duration> setup() {
        return validityMap;
    }

    public static class Group {
        private final TokenType type;
        private final TokenValidityConfigurer configurer;

        public Group(TokenValidityConfigurer configurer, TokenType type) {
            this.type = type;
            this.configurer = configurer;
        }

        public TokenValidityConfigurer withValidity(Duration validity) {
            configurer.validityMap.put(type, validity);
            return configurer;
        }
    }
}
