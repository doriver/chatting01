package com.example.chatting01.v02.controller;

import com.example.chatting01.v02.entity.ChatRoom02;
import com.example.chatting01.v02.service.ChatRoomService;
import com.example.chatting01.v02.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/v02/api")
public class Api02Controller {

    @Autowired private ChatRoomService chatRoomService;
    @Autowired private UserService userService;

    /*
        단톡방 입장하기
     */
    @PostMapping("/participant/{rid}/{uid}")
    public String enterRoom(@PathVariable("rid") long rid, @PathVariable("uid") long uid
            , RedirectAttributes reAtr) {
        chatRoomService.userEnterRoom(rid, uid);

        reAtr.addAttribute("rid", rid);
        return "redirect:/v02/chatRoom";
    }

    /*
        채팅방 나가기
     */
    @PatchMapping("/rooms/{roomId}/{chatterId}")
    @ResponseBody
    public void exitRoom(@PathVariable("roomId") long roomId, @PathVariable("chatterId") long chatterId) {
        chatRoomService.chatterExitRoom(roomId, chatterId);
    }

    /*
        채팅방 종료, 서버에서 클라이언트 웺소켓 종료하는거 시간 걸릴수도
     */
    @PatchMapping("/rooms/{roomId}")
    @ResponseBody
    public void endRoom(@PathVariable("roomId") long roomId) {
        chatRoomService.mentorEndRoom(roomId);
    }


    /*
        사용자 로그인
     */
    @GetMapping("/login/{id}")
    @ResponseBody
    public String userLogin(HttpSession session, @PathVariable("id") long id) {
        String result = userService.findUserAndLogin(session, id);
        return result;
    }

    /*
        단체 채팅방 생성
     */
    @PostMapping("/room/{mid}")
    @ResponseBody
    public void createRoom( @PathVariable("mid") long mid
            , @RequestParam("roomName") String roomName, @RequestParam("userLimit") int userLimit) {
        chatRoomService.mentorCreateRoom(mid, roomName, userLimit);
    }

    /*
        단체 채팅방 목록
     */
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom02> roomList() {
        return chatRoomService.getChatRoomList();
    }


}
