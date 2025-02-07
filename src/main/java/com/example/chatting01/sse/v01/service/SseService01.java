package com.example.chatting01.sse.v01.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseService01 {

    // 주로 순회가 일어나는 용도로 사용할 때는 안전한 스레드 처리를 위해 CopyOnWriteArrayList를 사용
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public void addEmitter(SseEmitter emitter) {
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
    }

//    @Scheduled(fixedDelay = 1000) // 1초마다 실행
    public void sendEvents() {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send("Hello, World!");
            } catch (IOException e) {
                emitter.complete();
                emitters.remove(emitter);
            }
        }
    }
}
