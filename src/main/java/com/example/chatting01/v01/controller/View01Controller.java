package com.example.chatting01.v01.controller;

import com.example.chatting01.v01.entity.Role;
import com.example.chatting01.v01.entity.User01;
import com.example.chatting01.v01.repository.User01Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/v01")
public class View01Controller {

    @RequestMapping("/home")
    public String aa() {
        return "chatting/v01/chatHome01";
    }

    @Autowired
    private User01Repository user01Repository;

    /*
    프론트에서 form태그 쓰면 브라우저 url이 바뀌어서 redirect해줘야함
     */
    @PostMapping("/user")
    public String aa12 (@RequestParam("userName") String userName) {
        Role userRole = Role.USER;

        User01 uu = User01.builder()
                .userName(userName).role(userRole)
                .build();

        user01Repository.save(uu);
        return "redirect:/v01/home";
    }

    @PostMapping("/mentor")
    public String aa (@RequestParam("userName") String userName) {
        Role userRole = Role.MENTOR;

        User01 uu = User01.builder()
                .userName(userName).role(userRole)
                .build();

        user01Repository.save(uu);
        return "redirect:/v01/home";
    }

}
