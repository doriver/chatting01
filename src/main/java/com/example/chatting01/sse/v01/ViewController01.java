package com.example.chatting01.sse.v01;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sse")
public class ViewController01 {

    @GetMapping("/connect/view/123")
    public String a123a() {
        return "sse/v01/sseConnectView123";
    }
    @GetMapping("/connect/view/987")
    public String a125a() {
        return "sse/v01/sseConnectView987";
    }

    @GetMapping("/test/view")
    public String a1555a() {
        return "sse/v01/sseTestView";
    }

    @GetMapping("/view")
    public String aa() {
        return "sse/v01/sseView";
    }
}
