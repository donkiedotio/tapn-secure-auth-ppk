package io.donkie.tapn.secure.core;

import io.donkie.tapn.secure.models.TokenType;
import io.donkie.tapn.secure.models.config.TokenIssuerDraft;
import io.donkie.tapn.secure.config.TapnTokenProperties;
import org.apache.logging.log4j.util.InternalApi;

import java.time.Duration;
import java.util.Map;

@InternalApi
final class TokenIssuerEnricher {

    /**
     * Returns a Partially enriched TokenIssuerDraft based on the provided properties.
     * The properties are merged with the default values from TokenDefaults.
     * <p>
     *
     * @param properties The properties to merge with the default values.
     * @return A Partially enriched TokenIssuerDraft.
     */
    static TokenIssuerDraft enrichFromProperties(TapnTokenProperties properties) {

        return TokenIssuerDraft.draft()
                .withId(getPropertyOrDefault(
                        properties.getId(),
                        TokenDefaults.ID))

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

                .withDeliveryMode(getPropertyOrDefault(
                        properties.getDeliveryMode(),
                        TokenDefaults.DELIVERY_MODE))

                .build();
    }

    private static <T> T getPropertyOrDefault(T userDefined, T defaultValue) {
        return userDefined != null ? userDefined : defaultValue;
    }

    private static Map<TokenType, Duration> mergeMap(Map<TokenType, Duration> sourceMap, Map<TokenType, Duration> defaultMap) {
        var mergedMap = new java.util.HashMap<>(defaultMap);
        if (sourceMap != null && !sourceMap.isEmpty()) {
            mergedMap.putAll(sourceMap);
        }
        return mergedMap;
    }

    /**
     * Merges the partially enriched draft with the user-defined draft.
     * <p>
     *
     * @param partiallyEnrichedDraft The partially enriched draft to merge.
     * @param userDefinedDraft       The user-defined draft to merge.
     * @return A merged TokenIssuerDraft.
     */
    static TokenIssuerDraft enrichFromUserOverrides(TokenIssuerDraft partiallyEnrichedDraft, TokenIssuerDraft userDefinedDraft) {
        return TokenIssuerDraft.draft()
                .withId(getPropertyOrDefault(
                        userDefinedDraft.getId(),
                        partiallyEnrichedDraft.getId()))

                .withIssuer(getPropertyOrDefault(
                        userDefinedDraft.getIssuer(),
                        partiallyEnrichedDraft.getIssuer()))

                .withAlgorithm(getPropertyOrDefault(
                        userDefinedDraft.getAlgorithm(),
                        partiallyEnrichedDraft.getAlgorithm()))

                .withTokensAndValidity(mergeMap(
                        userDefinedDraft.getTokenValidityMap(),
                        partiallyEnrichedDraft.getTokenValidityMap()))

                .withTokensAndNbf(mergeMap(
                        userDefinedDraft.getNbfMap(),
                        partiallyEnrichedDraft.getNbfMap()))

                .withDeliveryMode(getPropertyOrDefault(
                        userDefinedDraft.getDeliveryMode(),
                        partiallyEnrichedDraft.getDeliveryMode()))

                .build();
    }
}
