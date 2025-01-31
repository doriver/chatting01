package com.example.chatting01.v02.entity;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "roomid")
    private GroupChatRoom01 room;

    @ManyToOne
    @JoinColumn(name = "senderid")
    private Chatter01 sender;

    private String message;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
    private LocalDateTime sendAt;

}
