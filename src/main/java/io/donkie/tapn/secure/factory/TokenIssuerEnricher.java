package io.donkie.tapn.secure.factory;

import lombok.AllArgsConstructor;
import io.donkie.tapn.secure.config.TapnTokenProperties;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TokenIssuerEnricher {

    private final TapnTokenProperties tokenProperties;

    // needs logic to validate the data of the TokenIssuerDraft and add defaults where required.

}
