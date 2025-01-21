package com.example.chatting01.v01.repository;

import com.example.chatting01.v01.entity.Chatter01;
import com.example.chatting01.v01.entity.User01;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Chatter01Repository extends JpaRepository<Chatter01, Long> {

    Optional<Chatter01> findByChatter(User01 chatter);
}
