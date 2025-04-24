package io.donkie.tapn.secure.core;

import io.donkie.tapn.secure.config.TapnTokenProperties;
import io.donkie.tapn.secure.jwt.JwtGeneratorService;
import io.donkie.tapn.secure.models.config.TokenIssuerDraft;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public final class TokenIssuerAutoConfigurer {

    private final TapnTokenProperties tokenProperties;
    private final List<TokenIssuerDraft> drafts;
    private final ConfigurableApplicationContext context;
    private final JwtGeneratorService jwtGeneratorService;

    Logger log = LoggerFactory.getLogger(TokenIssuerAutoConfigurer.class);

    public TokenIssuerAutoConfigurer(
            TapnTokenProperties tokenProperties,
            List<TokenIssuerDraft> drafts,
            ConfigurableApplicationContext context,
            JwtGeneratorService jwtGeneratorService) {
        this.tokenProperties = tokenProperties;
        this.drafts = drafts;
        this.context = context;
        this.jwtGeneratorService = jwtGeneratorService;
    }

    /*
     * TODO:Need to update logic to support multiple Token Issuer Draft configuration through the application.yml
     */
    @PostConstruct
    public void initTokenIssuer() {
        drafts.forEach(draft -> {
            var partiallyEnrichedDraft = TokenIssuerEnricher.enrichFromProperties(tokenProperties);
            var fullyEnrichedDraft = TokenIssuerEnricher.enrichFromUserOverrides(partiallyEnrichedDraft, draft);

            var beanName = fullyEnrichedDraft.getId();
            var tokenIssuer = new TokenIssuer(createTokenIssuerAware(fullyEnrichedDraft), jwtGeneratorService);

            if (context.containsBean(beanName))
                log.error("Duplicate TokenIssuer Id. Token issuer bean already exists with name: {}", Optional.ofNullable(beanName));
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
