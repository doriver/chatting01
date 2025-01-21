package com.example.chatting01.v01.controller;

import com.example.chatting01.v01.entity.Chatter01;
import com.example.chatting01.v01.entity.GroupChatRoom01;
import com.example.chatting01.v01.entity.Role;
import com.example.chatting01.v01.entity.User01;
import com.example.chatting01.v01.repository.Chatter01Repository;
import com.example.chatting01.v01.repository.GroupChatRoom01Repository;
import com.example.chatting01.v01.repository.User01Repository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/v01/api")
public class ApiController {

    @Autowired
    private User01Repository user01Repository;

    @Autowired private GroupChatRoom01Repository groupChatRoom01Repository;
    @Autowired private Chatter01Repository chatter01Repository;

    @GetMapping("/login/{id}")
    @ResponseBody
    public void a123a(HttpSession session, @PathVariable("id") long id) {
        Optional<User01> loginUser = user01Repository.findById(id);
        session.setAttribute("loginUser", loginUser.get());
    }

    @PostMapping("/room/{mid}")
    @ResponseBody
    public void a112323a( @PathVariable("mid") long mid
            , @RequestParam("roomName") String roomName, @RequestParam("userLimit") int userLimit) {
        User01 mentor = user01Repository.findById(mid).orElse(null);

        if (mentor != null && mentor.getRole().name() == "MENTOR") {
            GroupChatRoom01 room = GroupChatRoom01.builder()
                    .mentor(mentor).roomName(roomName).limitNumber(userLimit)
                    .build();
            groupChatRoom01Repository.save(room);
        } else {
            // throw new Exception();
        }
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<GroupChatRoom01> asdf() {
        List<GroupChatRoom01> roomList = groupChatRoom01Repository.findAll();
        return roomList;
    }

    @PostMapping("/participant/{rid}/{uid}")
    public String aasd323a( @PathVariable("rid") long rid, @PathVariable("uid") long uid) {



        GroupChatRoom01 chatRoom = groupChatRoom01Repository.findById(rid).orElse(null);
        User01 participant = user01Repository.findById(uid).orElse(null);
        // 로그인 여부까지 체크하면 좋음

        Optional<Chatter01> attendance = chatter01Repository.findByChatter(participant);

        if (attendance.isEmpty()) {
            if (participant != null && chatRoom != null) {
                Chatter01 chatter = Chatter01.builder()
                        .room(chatRoom).chatter(participant)
                        .build();
                chatter01Repository.save(chatter);
            } else {
                // throw new Exception();
            }
        }


        return "redirect:/v01/chr";
    }
}
