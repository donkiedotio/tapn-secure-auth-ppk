package io.donkie.tapn.secure.config;

import io.donkie.tapn.secure.models.DeliveryMode;
import io.donkie.tapn.secure.models.SignatureAlgorithm;
import io.donkie.tapn.secure.models.TokenType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@Component
@ConfigurationProperties("tapn.token")
public class TapnTokenProperties {
    private String id;
    private String issuer;
    private SignatureAlgorithm algorithm;
    private DeliveryMode deliveryMode;
    private Map<TokenType, Duration> validity = new HashMap<>();
    private Map<TokenType, Duration> nbf = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public SignatureAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(SignatureAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public DeliveryMode getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(DeliveryMode deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public Map<TokenType, Duration> getValidity() {
        return validity;
    }

    public void setValidity(Map<TokenType, Duration> validity) {
        this.validity = validity;
    }

    public Map<TokenType, Duration> getNbf() {
        return nbf;
    }

    public void setNbf(Map<TokenType, Duration> nbf) {
        this.nbf = nbf;
    }
}

