package io.donkie.tapn.secure.factory;

import io.donkie.tapn.secure.core.TokenIssuer;
import io.donkie.tapn.secure.models.config.TokenIssuerDraft;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class TokenIssuerFactory {

    public TokenIssuer initTokenIssuer(Supplier<TokenIssuerDraft> configurerSupplier) {
        // enrich the token issuer with default's
        // build-immutable TokenIssuerAware and build TokenIssuer and return
        return null;
    }
}
