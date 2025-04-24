package io.donkie.tapn.secure.core;

import io.donkie.tapn.secure.jwt.JwtGeneratorService;
import io.donkie.tapn.secure.jwt.JwtPayload;
import io.donkie.tapn.secure.models.TokenType;
import io.donkie.tapn.secure.utility.CurrentUser;

import java.time.Instant;
import java.util.Map;

class TokenIssuer {

    private final TokenIssuerAware tokenIssuerAware;
    private final JwtGeneratorService jwtGeneratorService;

    public TokenIssuer(TokenIssuerAware tokenIssuerAware, JwtGeneratorService jwtGeneratorService) {
        this.tokenIssuerAware = tokenIssuerAware;
        this.jwtGeneratorService = jwtGeneratorService;
    }

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
     *
     * Important: We should be able to blacklist tokens.
     *
     * Description:
     * 1. The tokens should be tracked to blacklist them?
     *      - We can blacklist the tokens when the user wants to log out from the current device.
     *         This is easy because we should blacklist the tokens that are currently associated with
     *         the user.
     *      - But, when we want to enable user to log out from all devices or specific device,
     *         - We have device-Id given to each device.
     *         - We somehow have to associate the device-Ids with the user, so that we can say
     *              - Logout user from all devices excluding the current device, (find all device-Ids associated with the user
     *                excluding the current one.)
     *              - Now that we have the list of device-Ids, we are still not capable of blacklisting the tokens
     *                as we still have to figure out a way to find the associated tokens.
     *              - So, we need to think of a way to associate the device-Ids with the tokens.
     *              - But the JWTs are Stateless. They can be accessed when the user sends them to the server. So the tokens
     *                cannot be accessed and blacklisted. Now if I have to enable that, I may have to associate all the
     *                JTIs to the device-Id and then blacklist them. Here, if the client loses the device-Id, the application
     *                would stop recognizing him.
     * */

    public void issueAsBearer() {

        var subject = CurrentUser.getPrincipal().orElseThrow(); // todo: must through an exception
        var authorities = CurrentUser.getAuthorities();
        Map<String, Object> additionalClaims = Map.of(
                "authorities", authorities
        );
        var issueTime = Instant.now();

        var accessExpiration = issueTime.plus(tokenIssuerAware.getTokenValidityMap().get(TokenType.ACCESS));
        JwtPayload accessPayload = new JwtPayload(
                subject,
                additionalClaims,
                issueTime,
                accessExpiration,
                null,
                tokenIssuerAware.getAlgorithm()
        );
        var accessToken = jwtGeneratorService.generateToken(accessPayload);

        var refreshExpiration = issueTime.plus(tokenIssuerAware.getTokenValidityMap().get(TokenType.REFRESH));
        JwtPayload refreshPayload = new JwtPayload(
                subject,
                additionalClaims,
                issueTime,
                refreshExpiration,
                null,
                tokenIssuerAware.getAlgorithm()
        );
        var refreshToken = jwtGeneratorService.generateToken(refreshPayload);
    }

    public void issueAsCookies() {
        // todo: implement this method
    }
}
