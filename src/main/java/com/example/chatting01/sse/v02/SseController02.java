package com.example.chatting01.sse.v02;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
    SSE Emitter 설정
 */
@RestController
@RequestMapping("/sse")
public class SseController02 {
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    @GetMapping("/subscribe/{connectId}")
    public SseEmitter subscribe(@PathVariable("connectId") String connectId) {
        SseEmitter emitter = new SseEmitter(60_000L); // 60초 타임아웃
        emitters.put(connectId, emitter);

        emitter.onCompletion(() -> emitters.remove(connectId));
        emitter.onTimeout(() -> emitters.remove(connectId));

        return emitter;
    }



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
}
