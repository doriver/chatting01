package com.example.chatting01.sse.v01;


import com.example.chatting01.sse.v01.service.SseService01;
import com.example.chatting01.sse.v01.service.SseService02;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
@RequestMapping("/sse/connect")
@RequiredArgsConstructor
public class SseConnectController01 {

    private final SseService01 sseService01;
    private final SseService02 sseService02;

    @GetMapping(path = "/02/{connectId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public SseEmitter subscribe(@PathVariable("connectId") String connectId) {
        SseEmitter emitter = new SseEmitter(60_000L); // 60초 타임아웃
        sseService02.addEmitter(emitter, connectId);
        return emitter; // 어디로 가는지는 모름( 프론트로 가는지 그냥 서버에만 있는지 )
    }

    // UTF-8 데이터만 보낼 수 있음, 바이너리 데이터 지원 x
    @GetMapping(path = "/01", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public SseEmitter subscribe(){
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); //SseEmitter는 서버에서 클라이언트로 이벤트를 전달할 수 있다.
        sseService01.addEmitter(emitter);
        sseService01.sendEvents(); // @Scheduled 걸었던거 실행키는거
        return emitter;
    }
}
