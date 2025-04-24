package io.donkie.tapn.secure.session;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DeviceFingerprintBeanConfigFallback {

    @Bean
    @ConditionalOnMissingBean
    DeviceFingerprintGenerator deviceFingerprintGenerator(){
        return request -> {
            String deviceId = safeHeader(request, "X-Device-ID");
            // fallback if X-Device-ID is not present
            deviceId = fallbackOrThrow(request, deviceId);

            String language = safeHeader(request, "Accept-Language");
            String platform = extractPlatform(request);

            var raw = deviceId + "|" + language + "|" + platform;
            return DigestUtils.sha256Hex(raw);
        };
    }

    private String fallbackOrThrow(HttpServletRequest request, String deviceId) {
        if(deviceId.equals(UNKNOWN))
            deviceId = safeHeader(request, "User-Agent");
        if(deviceId.equals(UNKNOWN))
            throw new IllegalArgumentException("Could not identify the client, X-Device-ID or User-Agent headers mandatory.");
        return deviceId;
    }

    @Bean
    @ConditionalOnMissingBean
    DeviceFingerprintValidator deviceFingerprintValidator(DeviceFingerprintGenerator fingerprintGenerator) {
        return (request, session) -> {
            var fingerprint = fingerprintGenerator.generateFingerprint(request);
            return session.getDeviceFingerPrint().equals(fingerprint);
        };
    }

    private final String UNKNOWN = "unknown";
    private String safeHeader(HttpServletRequest request, String name) {
        String value = request.getHeader(name);
        return value != null ? value.trim().toLowerCase() : UNKNOWN;
    }

    private String extractPlatform(HttpServletRequest request) {
        String platform = request.getHeader("Sec-CH-UA-Platform");
        if (platform != null && !platform.isBlank()) {
            return platform.replace("\"", "").toLowerCase();
        }
        // Fallback
        return extractPlatformFromUserAgent(request.getHeader("User-Agent"));
    }

    private String extractPlatformFromUserAgent(String userAgent) {
        if (userAgent == null) return "unknown";
        if (userAgent.contains("Windows")) return "windows";
        if (userAgent.contains("Mac")) return "mac";
        if (userAgent.contains("X11")) return "unix";
        if (userAgent.contains("Android")) return "android";
        if (userAgent.contains("iPhone")) return "ios";
        return "other";
    }
}
