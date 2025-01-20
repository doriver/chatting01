package com.example.chatting01.v01.controller;

import com.example.chatting01.v01.entity.Role;
import com.example.chatting01.v01.entity.User01;
import com.example.chatting01.v01.repository.User01Repository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/v01")
public class FirstView01Controller {

    @Autowired
    private User01Repository user01Repository;

    @RequestMapping("/ex01")
    public String aa(Model model) {
        List<User01> userList = user01Repository.findAll();
        model.addAttribute("userList", userList);
        return "chatting/v01/chatEx01";
    }

    @RequestMapping("/chr")
    public String aachr(Model model, @RequestParam(name="uid") long uid) {
//        List<User01> userList = user01Repository.findAll();
//        model.addAttribute("userList", userList);
        return "chatting/v01/chatRoom01";
    }



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
        return "redirect:/v01/ex01";
    }

    @PostMapping("/mentor")
    public String aa (@RequestParam("userName") String userName) {
        Role userRole = Role.MENTOR;

        User01 uu = User01.builder()
                .userName(userName).role(userRole)
                .build();

        user01Repository.save(uu);
        return "redirect:/v01/ex01";
    }

}
