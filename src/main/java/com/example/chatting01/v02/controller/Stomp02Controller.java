package com.example.chatting01.v02.controller;

import com.example.chatting01.v01.dto.RecieveMessage01DTO;
import com.example.chatting01.v01.dto.SendMessage01DTO;
import com.example.chatting01.v01.entity.User01;
import com.example.chatting01.v02.dto.RecieveMessage02DTO;
import com.example.chatting01.v02.dto.SendMessage02DTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@Slf4j
public class Stomp02Controller {

    /*
    @MessageMapping 메서드에서는 일반적인 HTTP기반의 HttpSession을 직접 주입받을 수 없다.

     */
    @MessageMapping("/{roomId}")
    @SendTo("/chatRoom/{roomId}")
    public RecieveMessage02DTO hello(SendMessage02DTO sendMessage02DTO
                                , @DestinationVariable("roomId") long roomId) {

        String sender = sendMessage02DTO.getSender();

        RecieveMessage02DTO msg = RecieveMessage02DTO.builder()
                .content(sendMessage02DTO.getMessage()).sender(sender).sendedAt(LocalDateTime.now())
                .build();

        return msg;
    }
}
