package io.donkie.tapn.secure.factory.util;

import io.donkie.tapn.secure.config.TapnTokenProperties;
import io.donkie.tapn.secure.core.TokenDefaults;
import io.donkie.tapn.secure.models.TokenType;
import io.donkie.tapn.secure.models.config.TokenIssuerDraft;

import java.time.Duration;
import java.util.Map;

public final class TokenIssuerDetailMerger {

    private TokenIssuerDetailMerger() {
    }

    public static TokenIssuerDraft merge(TapnTokenProperties properties) {

        return TokenIssuerDraft.draft()
                .shouldIdentifyBy(getPropertyOrDefault(
                        properties.getIssuer(),
                        TokenDefaults.QUALIFIED_ISSUER_NAME))

                .withIssuer(getPropertyOrDefault(
                        properties.getIssuer(),
                        TokenDefaults.ISSUER))

                .withAlgorithm(getPropertyOrDefault(
                        properties.getAlgorithm(),
                        TokenDefaults.ALGORITHM))

                .withTokensAndValidity(mergeMap(
                        properties.getValidity(),
                        TokenDefaults.TOKEN_VALIDITY_MAP))

                .withTokensAndNbf(mergeMap(
                        properties.getNbf(),
                        TokenDefaults.NBF_MAP))

                .withDeliveryMode(
                        properties.getDeliveryMode() != null
                                ? properties.getDeliveryMode()
                                : TokenDefaults.DELIVERY_MODE)

                .build();
    }

    private static <T> T getPropertyOrDefault(T property, T defaultValue) {
        return property != null ? property : defaultValue;
    }

    private static Map<TokenType, Duration> mergeMap(Map<TokenType, Duration> sourceMap, Map<TokenType, Duration> defaultMap) {
        var mergedMap = new java.util.HashMap<>(defaultMap);
        if (sourceMap != null && !sourceMap.isEmpty()) {
            mergedMap.putAll(sourceMap);
        }
        return mergedMap;
    }
}
