package com.wp.live.domain.info.broadcast.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.openvidu.java.client.*;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.ParseException;
import java.util.Map;

class OpenviduProviderTest {
        @Test
        public void openviduAPITest() throws ParseException, JsonProcessingException, OpenViduJavaClientException,
                        OpenViduHttpException {
                WebClient webClient = WebClient.builder()
                                .baseUrl("MASKING_URL") // 기본 URL 설정
                                .defaultHeader("Authorization", "Basic MASKING_TOKEN") // 헤더 추가
                                .build();

                String block = webClient.get()
                                .uri("/openvidu/api/sessions/ses_GVfOYf9pmf")
                                .retrieve()
                                .bodyToMono(String.class)
                                .block();

                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> map = mapper.readValue(block, Map.class);

                OpenVidu openVidu = new OpenVidu("MASKING_URL", "MASKING_ID");
                SessionProperties build = SessionProperties.fromJson(map).build();
                Session session = openVidu.createSession(build);

                String serverData = "{\"serverData\": \"" + OpenViduRole.SUBSCRIBER + "\"}";
                ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
                                .type(ConnectionType.WEBRTC).data(serverData).role(OpenViduRole.SUBSCRIBER).build();
                String token = session.createConnection(connectionProperties).getToken();
                System.out.println(token);
                // block.createConnection().getToken();

                // System.out.println("Response: " + response);
        }
}