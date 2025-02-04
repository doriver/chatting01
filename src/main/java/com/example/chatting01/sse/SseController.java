package com.example.chatting01.sse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
public class SseController {
    @Autowired
    private SseService sseService;

    @GetMapping("/sse/view")
    public String aa() {
        return "sse/sseView";
    }

    // UTF-8 데이터만 보낼 수 있음, 바이너리 데이터 지원 x
    @GetMapping(path = "/emitter", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public SseEmitter subscribe(){
        //SseEmitter는 서버에서 클라이언트로 이벤트를 전달할 수 있습니다.
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        sseService.addEmitter(emitter);
        sseService.sendEvents();
        return emitter;
    }
}
