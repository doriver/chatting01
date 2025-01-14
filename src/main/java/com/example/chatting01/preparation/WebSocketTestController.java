package com.example.chatting01.preparation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wwss")
public class WebSocketTestController {

    @GetMapping("/echo")
    public String echo() {
        return "preparation/echo";
    }
    @GetMapping("/echo2")
    public String echo2() {
        return "preparation/echo2";
    }
}
