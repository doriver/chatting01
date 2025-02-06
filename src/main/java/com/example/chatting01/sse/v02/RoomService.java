package com.example.chatting01.sse.v02;

import org.springframework.stereotype.Service;

/*
    enterRoom() 실행시 이벤트 전송
 */
@Service
public class RoomService {
    private final SseController02 sseController;

    public RoomService(SseController02 sseController) {
        this.sseController = sseController;
    }

    public void sampleMethod(String connectId) {

        System.out.println("hello SSE");

        // SSE 이벤트 전송
        sseController.sendEvent(connectId, "hello SSE");
    }
}
