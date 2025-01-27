package com.example.chatting01.v01.repository;

import com.example.chatting01.v01.entity.Chatter01;
import com.example.chatting01.v01.entity.GroupChatRoom01;
import com.example.chatting01.v01.entity.User01;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface Chatter01Repository extends JpaRepository<Chatter01, Long> {

    Optional<Chatter01> findByChatterAndRoom(User01 chatter, GroupChatRoom01 room);

    Optional<Chatter01> findByChatter(User01 chatter);

    List<Chatter01> findAllByRoomAndExitTime(GroupChatRoom01 room, LocalDateTime exitTime);
    // 단톡방 현재 참석자들 가져오는거 : exitTime = null
}
