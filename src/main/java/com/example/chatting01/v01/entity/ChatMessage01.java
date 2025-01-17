package com.example.chatting01.v01.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage01 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cmid;

    private GroupChatRoom01 room;
    private User01 sender;

    private String message;
    private LocalDateTime timestamp;

}
