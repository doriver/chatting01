package com.example.chatting01.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hh")
    public String aa() {
        return "helllllo";
    }
}
