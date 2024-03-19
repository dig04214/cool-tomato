package com.wp.analyze.domain.realtime.utils;

import io.openvidu.java.client.OpenVidu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.json.JSONObject;

@Component
public class OpenviduConnection {

    private WebClient webClient;
    private String url;
    private String password;

    public OpenviduConnection(@Value("${openvidu.url}")String url, @Value("${openvidu.password}")String password){
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
        this.url = url;
        this.password = password;
    }

    public Long getConnectionNum(String sessionId){
        String result = webClient.get()
                .uri("/openvidu/api/sessions/" + sessionId + "/connection")
                .header("Authorization", "Basic " + password)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        JSONObject jsonObject = new JSONObject(result);
        return Long.parseLong(jsonObject.get("numberOfElements").toString());
    }
}
