package com.example.chatting01.v01.controller;

import com.example.chatting01.v01.dto.RecieveMessage01DTO;
import com.example.chatting01.v01.dto.SendMessage01DTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class Stomp01Controller {

    @MessageMapping("/hello")
    @SendTo("/topic/chatRoom")
    public RecieveMessage01DTO hello(SendMessage01DTO sendMessage01DTO) {

        RecieveMessage01DTO msg = RecieveMessage01DTO.builder()
                .content(sendMessage01DTO.getMessage()).sender(sendMessage01DTO.getSender()).sendedAt(LocalDateTime.now())
                .build();

        return msg;
    }
}
