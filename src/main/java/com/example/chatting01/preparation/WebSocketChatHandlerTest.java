package com.example.chatting01.preparation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component // 클라이언트가 발송한 메시지 받아서 치리해줄 Handler
public class WebSocketChatHandlerTest extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // client에게 받은 메시지
        String payload = message.getPayload();
        log.info("payload {}",payload);

        // 모든 클라이언트에게 메세지 전달
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage("Echo: " + payload));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    /*
        @Override
        afterConnectionEstablished(WebSocketSession session)

        handleMessage(WebSocketSession session, WebSocketMessage<?> message)

        handleTextMessage(WebSocketSession session, TextMessage message)

        handleBinaryMessage(WebSocketSession session, BinaryMessage message)

        handlePongMessage(WebSocketSession session, PongMessage message)

        handleTransportError(WebSocketSession session, Throwable exception)

        afterConnectionClosed(WebSocketSession session, CloseStatus status)
     */

}
