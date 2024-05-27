package com.wp.mediate.domain.openvidu.service;

import com.wp.mediate.global.common.code.ErrorCode;
import com.wp.mediate.global.exception.BusinessExceptionHandler;
import io.openvidu.java.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class OpenviduServiceImpl implements OpenviduService{

    private OpenVidu openVidu;
    private Map<String, Session> mapSessions = new ConcurrentHashMap<>();
    private String OPENVIDU_URL;
    private String SECRET;


    public OpenviduServiceImpl(@Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl) {
        this.SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
    }

    @Override
    public String createSession(){
        try {
            Session session = this.openVidu.createSession();
            this.mapSessions.put(session.getSessionId(), session);
            return session.getSessionId();
        } catch (Exception e) {
            throw new BusinessExceptionHandler(ErrorCode.BAD_REQUEST_ERROR);
        }
    }
    @Override
    public String getToken(String sessionId, OpenViduRole role) {
        String serverData = "{\"serverData\": \"" + role + "\"}";
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC).data(serverData).role(role).build();
        if (this.mapSessions.get(sessionId) != null) {
            try {
                return this.mapSessions.get(sessionId).createConnection(connectionProperties).getToken();
            } catch (OpenViduJavaClientException e1) {
                throw new BusinessExceptionHandler(ErrorCode.BAD_REQUEST_ERROR);
            } catch (OpenViduHttpException e2) {
                throw new BusinessExceptionHandler(ErrorCode.BAD_REQUEST_ERROR);
            }
        }
        else{
            throw new BusinessExceptionHandler(ErrorCode.NOT_FOUND_ERROR);
        }
    }

    @Override
    public void removeSession(String sessionId) {
        if (this.mapSessions.get(sessionId) != null) {
            this.mapSessions.remove(sessionId);
        }
    }
}
