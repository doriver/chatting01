package com.example.chatting01.v01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecieveMessage01DTO {
    private String content;
    private String sender;
    private LocalDateTime sendedAt;

    private int isMine; // 1: 내가쓴 글, 0: 상대방이 쓴 글


}
