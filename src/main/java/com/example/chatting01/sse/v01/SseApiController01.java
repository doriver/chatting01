package com.example.chatting01.sse.v01;

import com.example.chatting01.sse.v01.service.SseService02;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*

 */
@RestController
@RequestMapping("/sse/test")
@RequiredArgsConstructor
public class SseApiController01 {

    private final SseService02 sseService02;

    @GetMapping("/{connectId}")
    public void asdf( @PathVariable("connectId") String connectId ) {
        sseService02.sendEvent(connectId,"hello SSE");
    }

}
