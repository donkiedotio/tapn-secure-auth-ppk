package io.donkie.tapn.secure.core;

import io.donkie.tapn.secure.jwt.JwtGeneratorService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class TokenIssuer {

    private final TokenIssuerAware tokenIssuerAware;
    private final JwtGeneratorService jwtGeneratorService;

    /*
     * Need to support two ways of Token Delivery Mode
     * 1. Raw Text
     * 2. Cookie Headers
     *
     * The Best Way Could be to have two methods one for each delivery mode
     * 1. issueAsBearer
     * 2. issueAsCookie
     *
     * 1. issueAsBearer
     * - Generate Access and Refresh tokens including jti in them
     * - Create a Record that includes 1. Access Token, 2. Refresh Token, 3. username,
     *   4. userRole/authorities 5. access expiration, 6. refresh expiration
     * - Return the Record as response
     *
     * 2. issueAsCookie
     * - Generate Access and Refresh tokens including jti in them
     * - Add them to the HttpHeader as Cookies
     * - Return the HttpHeader as response
     * - The Client needs to send another request to get the authentication details.
     * */
}
