package com.example.chatting01.v02.service;

import com.example.chatting01.v01.entity.User01;
import com.example.chatting01.v01.repository.User01Repository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired private User01Repository user01Repository;

    public String findUserAndLogin(HttpSession session, long id) {
        User01 loginUser = user01Repository.findById(id).orElse(null);
        if (loginUser != null) {
            session.setAttribute("loginUser", loginUser);
            return loginUser.getUserName();
        }
        return "로그인 실패";
    }

}
