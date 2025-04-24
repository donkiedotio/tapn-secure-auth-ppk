package io.donkie.tapn.secure.session;

import jakarta.servlet.http.HttpServletRequest;

public interface DeviceFingerprintValidator {
    boolean validateFingerprint(HttpServletRequest request, DeviceSession session);
}
