package com.example.chatting01.v01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v01")
public class View01Controller {

    @RequestMapping("/home")
    public String aa() {
        return "chatting/v01/chatHome01";
    }
}
