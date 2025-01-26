package com.example.chatting01.v01;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfigV01 implements WebSocketMessageBrokerConfigurer {

    @Override // 메시지 브로커 설정
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 클라이언트가 구독할 수 있는 메시지 브로커를 설정. 메시지가 /topic으로 시작하는 경로로 전송되면 브로커가 이를 처리
        registry.enableSimpleBroker("/topic");
        // 클라이언트가 메시지를 보낼 때 사용할 prefix 설정. 클라이언트가 /app/hello로 메시지를 전송하면 이를 컨트롤러에서 처리.
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override // STOMP 엔드포인트 등록
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트는 해당 경로를 통해 WebSocket서버와 연결
        // 해당 경로에서 WebSocket연결을 수락
        registry.addEndpoint("/chatRoom-v01-websocket");
    }
}
