package com.wp.mediate.domain.openvidu.service;

import io.openvidu.java.client.OpenViduRole;

public interface OpenviduService {
    public String createSession();
    public String getToken(String sessionId, OpenViduRole role);
    public void removeSession(String sessionId);
}
