package com.example.chatting01.v01.controller;

import com.example.chatting01.v01.entity.Role;
import com.example.chatting01.v01.entity.User01;
import com.example.chatting01.v01.repository.GroupChatRoom01Repository;
import com.example.chatting01.v01.repository.User01Repository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v01/api")
public class ApiController {

    @Autowired
    private User01Repository user01Repository;

    @Autowired private GroupChatRoom01Repository groupChatRoom01Repository;

    @GetMapping("/login/{id}")
    public void a123a(HttpSession session, @PathVariable("id") long id) {
        Optional<User01> loginUser = user01Repository.findById(id);
        session.setAttribute("loginUser", loginUser.get());
    }

    @PostMapping("/room/{mid}")
    public void a112323a( @PathVariable("mid") long mid
            , @RequestParam("roomName") String roomName, @RequestParam("userLimit") String userLimit) {

    }
}
