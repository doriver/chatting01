package com.example.chatting01.sse.v01.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseService02 {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void addEmitter(SseEmitter emitter, String connectId) {
        emitters.put(connectId, emitter);
        emitter.onCompletion(() -> emitters.remove(connectId));
        emitter.onTimeout(() -> emitters.remove(connectId));
    }

    // 특정 id에게 보내기
    public void sendEvent(String connectId, String message) {
        SseEmitter emitter = emitters.get(connectId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("message").data(message));
            } catch (IOException e) {
                emitters.remove(connectId);
            }
        }
    }

    // 서버에 연결된 모든 클라이언트에게 보내기
    public void sendEventBroad(String message) {
        for (String key : emitters.keySet()) {

            SseEmitter emitter = emitters.get(key);
            if (emitter != null) {
                try {
                    emitter.send(SseEmitter.event().name("message").data(message));
                } catch (IOException e) {
                    emitters.remove(key);
                }
            }
        }
    }


}
