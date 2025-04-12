package io.donkie.tapn.secure.factory;

import io.donkie.tapn.secure.config.TapnTokenProperties;
import io.donkie.tapn.secure.core.TokenIssuer;
import io.donkie.tapn.secure.models.TokenIssuerAware;
import io.donkie.tapn.secure.models.config.TokenIssuerDraft;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@AllArgsConstructor
public class TokenIssuerFactory {

    private final TapnTokenProperties tokenProperties;

    public TokenIssuer initTokenIssuer(Supplier<TokenIssuerDraft> configurerSupplier) {
        var partiallyEnrichedDraft = TokenIssuerEnricher.enrichFromProperties(tokenProperties);
        var fullyEnrichedDraft = TokenIssuerEnricher.enrichFromUserOverrides(partiallyEnrichedDraft, configurerSupplier.get());
        var tokenIssuerAware = generateTokenIssuerAware(fullyEnrichedDraft);
        return new TokenIssuer(tokenIssuerAware);
    }

    private static TokenIssuerAware generateTokenIssuerAware(TokenIssuerDraft draft) {
        return TokenIssuerAware.builder()
                .issuer(draft.getIssuer())
                .algorithm(draft.getAlgorithm())
                .tokenValidityMap(draft.getTokenValidityMap())
                .nbfMap(draft.getNbfMap())
                .deliveryMode(draft.getDeliveryMode())
                .build();
    }
}
