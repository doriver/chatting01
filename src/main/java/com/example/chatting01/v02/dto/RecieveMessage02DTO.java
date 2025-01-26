package com.example.chatting01.v02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecieveMessage02DTO {
    private String content;
    private String sender;
    private LocalDateTime sendedAt;



}
