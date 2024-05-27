package com.wp.chat.global.config;

import com.wp.chat.global.common.service.FilterChannelInterceptor;
import com.wp.chat.global.common.service.StompExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker // Web Socket을 활성화하고 메시지 브로커 사용 가능
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private static final String WEB_SOCKET_HOST = "*";
    private final FilterChannelInterceptor filterChannelInterceptor;
    private final StompExceptionHandler stompExceptionHandler;

    // 메시지 브로커를 구성하는 메서드
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub");  // 메시지 구독 요청 prefix
        config.setApplicationDestinationPrefixes("/pub");   // 메시지 발행 요청 prefix
    }

    // STOMP 엔드포인트를 등록하는 메서드
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // stomp websocket endpoint 설정( ws://localhost:8085/v1/chat/ws-stomp )
        registry.setErrorHandler(stompExceptionHandler).addEndpoint("/v1/chat/ws-stomp").setAllowedOriginPatterns(WEB_SOCKET_HOST).withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(filterChannelInterceptor);
    }
}