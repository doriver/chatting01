package com.example.chatting01.v02.controller;

import com.example.chatting01.v01.entity.Chatter01;
import com.example.chatting01.v01.entity.GroupChatRoom01;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/v02/api")
public class Api02Controller {

    @Autowired
    private User01Repository user01Repository;

    @Autowired private GroupChatRoom01Repository groupChatRoom01Repository;
    @Autowired private Chatter01Repository chatter01Repository;

    /*
        채팅방 나가기
     */
    @PatchMapping("/rooms/{roomId}/{chatterId}")
    @ResponseBody
    public void exitRoom(@PathVariable("roomId") long roomId, @PathVariable("chatterId") long chatterId) {
        GroupChatRoom01 chatRoom = groupChatRoom01Repository.findById(roomId).orElse(null);
        User01 participant = user01Repository.findById(chatterId).orElse(null);

        if (participant != null && chatRoom != null) {
            Optional<Chatter01> attendance = chatter01Repository.findByChatterAndRoomAndExitTime(participant, chatRoom, null);
            if (!attendance.isEmpty()) {
                Chatter01 chatter01 = attendance.get();
                chatter01.setExitTime(LocalDateTime.now()); // 여기까지하면 update될줄 알았는데, 안됨
                chatter01Repository.save(chatter01);
            }
        }
    }


    /*
        사용자 로그인
     */
    @GetMapping("/login/{id}")
    @ResponseBody
    public String userLogin(HttpSession session, @PathVariable("id") long id) {
        Optional<User01> loginUser = user01Repository.findById(id);
        if (!loginUser.isEmpty()) {
            session.setAttribute("loginUser", loginUser.get());
            return loginUser.get().getUserName();
        }
        return "로그인 실패";
    }

    /*
        단체 채팅방 생성
     */
    @PostMapping("/room/{mid}")
    @ResponseBody
    public void createRoom( @PathVariable("mid") long mid
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

    /*
        단체 채팅방 목록
     */
    @GetMapping("/rooms")
    @ResponseBody
    public List<GroupChatRoom01> roomList() {
        List<GroupChatRoom01> roomList = groupChatRoom01Repository.findAll();
        return roomList;
    }

    /*
        단톡방 입장하기
     */
    @PostMapping("/participant/{rid}/{uid}")
    public String enterRoom(@PathVariable("rid") long rid, @PathVariable("uid") long uid
                        , RedirectAttributes reAtr) {
        // 일단은 단톡방으로 무조건 넘어가는 로직만 있음
        // 여러가지 경우를 고려해야할수도( 단톡방 없는경우 등 )
        GroupChatRoom01 chatRoom = groupChatRoom01Repository.findById(rid).orElse(null);
        User01 participant = user01Repository.findById(uid).orElse(null);
        // 로그인 여부까지 체크하면 좋음

        Optional<Chatter01> attendance = chatter01Repository.findByChatterAndRoomAndExitTime(participant, chatRoom, null);
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

        return "redirect:/v02/chatRoom";
    }
}
