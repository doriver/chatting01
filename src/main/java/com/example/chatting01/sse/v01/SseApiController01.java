package com.example.chatting01.sse.v01;

import com.example.chatting01.sse.v01.service.SseService02;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

/*

 */
@RestController
@RequestMapping("/sse/test")
@RequiredArgsConstructor
public class SseApiController01 {

    private final SseService02 sseService02;
    private final StringRedisTemplate template;

    @GetMapping("/{connectId}")
    public void asdf( @PathVariable("connectId") String connectId ) {
        sseService02.sendEvent(connectId,"hello SSE");
    }

    /*
        redis를 pub/sub을 이용해서 연결된 모든 사람에게 sse전송하기
     */
    @GetMapping("/redis")
    public void redf() {
        template.convertAndSend("sse", "Hello SSE from Redis!");
    }

}
