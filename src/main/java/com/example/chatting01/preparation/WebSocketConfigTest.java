package com.example.chatting01.preparation;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket // WebSocket 활성화
public class WebSocketConfigTest implements WebSocketConfigurer {

    private final WebSocketHandler webSocketChatHandlerTest;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketChatHandlerTest, "/echo2").setAllowedOrigins("*");
    }
}
