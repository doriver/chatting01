package com.example.chatting01.v02.controller;

import com.example.chatting01.v02.entity.ChatParticipant02;
import com.example.chatting01.v02.entity.ChatRoom02;
import com.example.chatting01.v02.entity.Role;
import com.example.chatting01.v02.entity.User01;
import com.example.chatting01.v02.repository.ChatParticipant02Repository;
import com.example.chatting01.v02.repository.ChatRoom02Repository;
import com.example.chatting01.v02.repository.User01Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/v02")
public class View02Controller {

    @Autowired
    private User01Repository user01Repository;
    @Autowired private ChatParticipant02Repository chatParticipant02Repository;
    @Autowired private ChatRoom02Repository chatRoom02Repository;

    /*
        유저, 단톡방 목록 화면
     */
    @RequestMapping("/list")
    public String chatList(Model model) {
        List<User01> userList = user01Repository.findAll();
        model.addAttribute("userList", userList);
        return "chatting/v02/chatList02";
    }

    /*
        단통방 화면
     */
    @RequestMapping("/chatRoom")
    public String aachr(@RequestParam("rid") long rid, Model model) {
        Optional<ChatRoom02> room = chatRoom02Repository.findById(rid);

        if (!room.isEmpty()) {
            List<ChatParticipant02> chatterList = chatParticipant02Repository.findAllByRoomAndExitTime(room.get(), null);
            model.addAttribute("chatterList", chatterList);
            model.addAttribute("room", room.get());
        }

        return "chatting/v02/chatRoom02";
    }



    /*
        프론트에서 form태그 쓰면 브라우저 url이 바뀌어서 redirect해줘야함
        회원가입(user)
     */
    @PostMapping("/user")
    public String aa12 (@RequestParam("userName") String userName) {
        Role userRole = Role.USER;

        User01 uu = User01.builder()
                .userName(userName).role(userRole)
                .build();

        user01Repository.save(uu);
        return "redirect:/v02/list";
    }

    /*
        회원가입(mentor)
     */
    @PostMapping("/mentor")
    public String aa (@RequestParam("userName") String userName) {
        Role userRole = Role.MENTOR;

        User01 uu = User01.builder()
                .userName(userName).role(userRole)
                .build();

        user01Repository.save(uu);
        return "redirect:/v02/list";
    }

}
