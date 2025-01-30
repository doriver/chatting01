package com.example.chatting01.v02.controller;

import com.example.chatting01.v01.dto.RecieveMessage01DTO;
import com.example.chatting01.v01.dto.SendMessage01DTO;
import com.example.chatting01.v01.entity.ChatMessage01;
import com.example.chatting01.v01.entity.Chatter01;
import com.example.chatting01.v01.entity.GroupChatRoom01;
import com.example.chatting01.v01.entity.User01;
import com.example.chatting01.v01.repository.ChatMessage01Repository;
import com.example.chatting01.v01.repository.Chatter01Repository;
import com.example.chatting01.v01.repository.GroupChatRoom01Repository;
import com.example.chatting01.v01.repository.User01Repository;
import com.example.chatting01.v02.dto.RecieveMessage02DTO;
import com.example.chatting01.v02.dto.SendMessage02DTO;
import com.example.chatting01.v02.service.ChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@Slf4j
public class Stomp02Controller {
    @Autowired private ChatMessageService chatMessageService;

    /*
        @MessageMapping 메서드에서는 일반적인 HTTP기반의 HttpSession을 직접 주입받을 수 없다.
     */
    @MessageMapping("/{roomId}")
    @SendTo("/chatRoom/{roomId}/message")
    public RecieveMessage02DTO hello(SendMessage02DTO sendMessage02DTO
                                , @DestinationVariable("roomId") long roomId) {

        long senderId = sendMessage02DTO.getSenderId();
        String sender = sendMessage02DTO.getSender();
        String message = sendMessage02DTO.getMessage();

        chatMessageService.saveMessage(roomId, senderId, message);

        RecieveMessage02DTO msg = RecieveMessage02DTO.builder()
                .content(message).sender(sender).sendedAt(LocalDateTime.now())
                .build();

        return msg;
    }
}
