package io.donkie.tapn.secure.config;

import lombok.Getter;
import lombok.Setter;
import io.donkie.tapn.secure.models.DeliveryMode;
import io.donkie.tapn.secure.models.SignatureAlgorithm;
import io.donkie.tapn.secure.models.TokenType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties("tapn.token")
public class TapnTokenProperties {
    private String issuer;
    private SignatureAlgorithm algorithm;
    private DeliveryMode deliveryMode;
    private Map<TokenType, Duration> validity = new HashMap<>();
    private Map<TokenType, Duration> nbf = new HashMap<>();
}

