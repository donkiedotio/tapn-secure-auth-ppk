package io.donkie.tapn.secure.core;

import lombok.AllArgsConstructor;
import io.donkie.tapn.secure.models.TokenIssuerAware;

@AllArgsConstructor
public class TokenIssuer {

    private final TokenIssuerAware tokenIssuerAware;

    // now needs sub, authorities, jti


    public static void main(String[] args) {
    }
}
