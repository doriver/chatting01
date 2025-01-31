package com.example.chatting01.v02.repository;

import com.example.chatting01.v02.entity.ChatRoom02;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatRoom02Repository extends JpaRepository<ChatRoom02, Long> {
    List<ChatRoom02> findAllByEndedAt(LocalDateTime endedAt);
}
