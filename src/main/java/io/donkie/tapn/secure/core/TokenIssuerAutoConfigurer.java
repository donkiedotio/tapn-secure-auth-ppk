package io.donkie.tapn.secure.core;

import io.donkie.tapn.secure.config.TapnTokenProperties;
import io.donkie.tapn.secure.models.config.TokenIssuerDraft;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public final class TokenIssuerAutoConfigurer {

    private final TapnTokenProperties tokenProperties;
    private final List<TokenIssuerDraft> drafts;
    private final ConfigurableApplicationContext context;

    @PostConstruct
    public void initTokenIssuer() {
        drafts.forEach(draft -> {
            var partiallyEnrichedDraft = TokenIssuerEnricher.enrichFromProperties(tokenProperties);
            var fullyEnrichedDraft = TokenIssuerEnricher.enrichFromUserOverrides(partiallyEnrichedDraft, draft);

            var beanName = fullyEnrichedDraft.getId();
            var tokenIssuer = new TokenIssuer(createTokenIssuerAware(fullyEnrichedDraft));

            if (context.containsBean(beanName))
                log.error("Duplicate TokenIssuer Id. Token issuer bean already exists with name: {}", beanName);
            else
                context.getBeanFactory().registerSingleton(beanName, tokenIssuer);
        });
    }

    private TokenIssuerAware createTokenIssuerAware(TokenIssuerDraft fullyEnrichedDraft) {
        return TokenIssuerAware.builder()
                .issuer(fullyEnrichedDraft.getIssuer())
                .algorithm(fullyEnrichedDraft.getAlgorithm())
                .tokenValidityMap(fullyEnrichedDraft.getTokenValidityMap())
                .nbfMap(fullyEnrichedDraft.getNbfMap())
                .deliveryMode(fullyEnrichedDraft.getDeliveryMode())
                .build();
    }
}
