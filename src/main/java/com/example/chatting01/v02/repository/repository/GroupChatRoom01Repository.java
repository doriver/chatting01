package com.example.chatting01.v02.repository.repository;

import com.example.chatting01.v02.entity.entity.GroupChatRoom01;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface GroupChatRoom01Repository extends JpaRepository<GroupChatRoom01, Long> {
    List<GroupChatRoom01> findAllByEndedAt(LocalDateTime endedAt);
}
