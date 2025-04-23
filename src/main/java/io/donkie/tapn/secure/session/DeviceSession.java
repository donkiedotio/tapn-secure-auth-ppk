package io.donkie.tapn.secure.session;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class DeviceSession implements Serializable {
    private String sessionId;
    private String userId;
    private String deviceFingerPrint;
    private Instant createdAt;

    public static DeviceSession create(String userId, String deviceFingerPrint) {
        DeviceSession session = new DeviceSession();
        session.sessionId = UUID.randomUUID().toString();
        session.userId = userId;
        session.deviceFingerPrint = deviceFingerPrint;
        session.createdAt = Instant.now();
        return session;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceFingerPrint() {
        return deviceFingerPrint;
    }

    public void setDeviceFingerPrint(String deviceFingerPrint) {
        this.deviceFingerPrint = deviceFingerPrint;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}