package io.donkie.tapn.secure.session;

import jakarta.servlet.http.HttpServletRequest;

public interface DeviceFingerprintGenerator {
    String generateFingerprint(HttpServletRequest request);
}
