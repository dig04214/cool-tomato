package com.wp.live.domain.broadcast.utils;

import com.wp.live.domain.broadcast.dto.utils.request.RegisterAlarmRequestDto;
import com.wp.live.domain.broadcast.dto.utils.request.ValidationRequestDto;
import com.wp.live.domain.broadcast.dto.utils.response.ResponseDto;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Component
public class UserConnection {
    private WebClient webClient;
    private String url;
    private SslContext context;
    private HttpClient httpClient;
    public UserConnection(@Value("${user.url}")String url) throws SSLException {
        context = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        httpClient = HttpClient.create().secure(provider -> provider.sslContext(context));
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
        this.url = url;
    }

    public void registerAlarm(Long sellerId, String topicId, String nickName, String accessToken){
        RegisterAlarmRequestDto request = RegisterAlarmRequestDto.builder().sellerId(sellerId).alarmUrl(topicId).content(nickName + "님 라이브 방송이 시작했습니다. 지금 바로 시청해보세요!").build();
        webClient.post()
                .uri("/v1/users/alarms")
                .header("Authorization", "Bearer " + accessToken)
                .body(Mono.just(request), RegisterAlarmRequestDto.class)
                .retrieve()
                .bodyToMono(ResponseDto.class)
                .block();
    }
}
