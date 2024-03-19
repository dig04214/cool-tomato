package com.wp.live.domain.broadcast.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MediateOpenviduConnection {

    private WebClient webClient;

    public MediateOpenviduConnection(@Value("${mediate.url}")String url){
        this.webClient = WebClient.create(url + "/v1/mediate/openvidu");
    }

    public String getToken(String sessionId, String role){
        return webClient.post()
                .uri("/token/"+sessionId+"/"+role)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getSessionId(){
        return webClient.post()
                .uri("/session")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public void deleteSession(String sessionId){
        webClient.delete()
                .uri("/session/" + sessionId)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe();
    }

}
