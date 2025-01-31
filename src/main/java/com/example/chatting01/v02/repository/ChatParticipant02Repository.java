package com.example.chatting01.v02.repository;

import com.example.chatting01.v02.entity.ChatParticipant02;
import com.example.chatting01.v02.entity.ChatRoom02;
import com.example.chatting01.v02.entity.User01;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ChatParticipant02Repository extends JpaRepository<ChatParticipant02, Long> {

    Optional<ChatParticipant02> findByChatterAndRoom(User01 chatter, ChatRoom02 room);
    Optional<ChatParticipant02> findByChatterAndRoomAndExitTime(User01 chatter, ChatRoom02 room, LocalDateTime exitTime);

    Optional<ChatParticipant02> findByChatter(User01 chatter);

    List<ChatParticipant02> findAllByRoomAndExitTime(ChatRoom02 room, LocalDateTime exitTime);
    // 단톡방 현재 참석자들 가져오는거 : exitTime = null
}
