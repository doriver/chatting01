package com.example.chatting01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @RequestMapping("/hh")
    @ResponseBody
    public String aa() {
        return "helllllo";
    }

    @RequestMapping("/vv")
    public String qq() {
        return "firstView";
    }
}
