package com.example.chatting01.v01.controller;

import com.example.chatting01.v01.dto.RecieveMessage01DTO;
import com.example.chatting01.v01.dto.SendMessage01DTO;
import com.example.chatting01.v01.entity.User01;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@Slf4j
public class Stomp01Controller {

    /*
    @MessageMapping 메서드에서는 일반적인 HTTP기반의 HttpSession을 직접 주입받을 수 없다.

     */
    @MessageMapping("/hello")
    @SendTo("/topic/chatRoom")
    public RecieveMessage01DTO hello(SendMessage01DTO sendMessage01DTO, SimpMessageHeaderAccessor headerAccessor) {
        // WebSocket 세션 속성에 접근
        String sessionId = headerAccessor.getSessionId();
        User01 loginUser = (User01)headerAccessor.getSessionAttributes().get("loginUser"); // 세션에 저장된 속성 접근( 세션 인터셉터 등록해야 사용 가능 )
        
        String loginUserName = loginUser.getUserName();
        String sender = sendMessage01DTO.getSender();

        log.info("  ======    ======  로그인 유저 : " + loginUserName);
        log.info("  ======    ======  보낸사람 : " + sender);

        int isMine = 0;
        if (loginUserName.equals(sender)) {
            isMine = 1;
        }

        RecieveMessage01DTO msg = RecieveMessage01DTO.builder()
                .content(sendMessage01DTO.getMessage()).sender(sender).sendedAt(LocalDateTime.now()).isMine(isMine)
                .build();

        return msg;
    }
}
