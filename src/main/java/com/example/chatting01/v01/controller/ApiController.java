package com.example.chatting01.v01.controller;

import com.example.chatting01.v01.entity.Chatter01;
import com.example.chatting01.v01.entity.GroupChatRoom01;
import com.example.chatting01.v01.entity.Role;
import com.example.chatting01.v01.entity.User01;
import com.example.chatting01.v01.repository.Chatter01Repository;
import com.example.chatting01.v01.repository.GroupChatRoom01Repository;
import com.example.chatting01.v01.repository.User01Repository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/v01/api")
public class ApiController {

    @Autowired
    private User01Repository user01Repository;

    @Autowired private GroupChatRoom01Repository groupChatRoom01Repository;
    @Autowired private Chatter01Repository chatter01Repository;

    @GetMapping("/login/{id}")
    @ResponseBody
    public String a123a(HttpSession session, @PathVariable("id") long id) {
        Optional<User01> loginUser = user01Repository.findById(id);
        if (!loginUser.isEmpty()) {
            session.setAttribute("loginUser", loginUser.get());
            return loginUser.get().getUserName();
        }
        return "로그인 실패";
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

    /*
        단톡방 입장하기
     */
    @PostMapping("/participant/{rid}/{uid}")
    public String aasd323a(@PathVariable("rid") long rid, @PathVariable("uid") long uid
                        , RedirectAttributes reAtr) {
        // 일단은 단톡방으로 무조건 넘어가는 로직만 있음
        // 여러가지 경우를 고려해야할수도( 단톡방 없는경우 등 )
        GroupChatRoom01 chatRoom = groupChatRoom01Repository.findById(rid).orElse(null);
        User01 participant = user01Repository.findById(uid).orElse(null);
        // 로그인 여부까지 체크하면 좋음

        Optional<Chatter01> attendance = chatter01Repository.findByChatterAndRoom(participant, chatRoom);
//        log.info(" =====   2   ======  어디까지 갈까?"); // 어디가 잘못됐는지 찾으려고 했던거 
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
        reAtr.addAttribute("rid", rid);

        return "redirect:/v01/chr";
    }
}
