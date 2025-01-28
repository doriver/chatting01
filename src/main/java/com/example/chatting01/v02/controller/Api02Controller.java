package com.example.chatting01.v02.controller;

import com.example.chatting01.v01.entity.Chatter01;
import com.example.chatting01.v01.entity.GroupChatRoom01;
import com.example.chatting01.v01.entity.User01;
import com.example.chatting01.v01.repository.Chatter01Repository;
import com.example.chatting01.v01.repository.GroupChatRoom01Repository;
import com.example.chatting01.v01.repository.User01Repository;
import com.example.chatting01.v02.service.ChatRoomService;
import com.example.chatting01.v02.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/v02/api")
public class Api02Controller {

    @Autowired private ChatRoomService chatRoomService;
    @Autowired private UserService userService;

    /*
        채팅방 나가기
     */
    @PatchMapping("/rooms/{roomId}/{chatterId}")
    @ResponseBody
    public void exitRoom(@PathVariable("roomId") long roomId, @PathVariable("chatterId") long chatterId) {
        chatRoomService.chatterExitRoom(roomId, chatterId);
    }

    /*
        개설자가 채팅방 종료
     */
    @PatchMapping("/rooms/{roomId}/{chatterId}")
    @ResponseBody
    public void endRoom(@PathVariable("roomId") long roomId, @PathVariable("chatterId") long chatterId) {

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
    public List<GroupChatRoom01> roomList() {
        return chatRoomService.getChatRoomList();
    }

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
}
