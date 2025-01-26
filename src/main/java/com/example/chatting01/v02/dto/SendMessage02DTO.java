package com.example.chatting01.v02.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMessage02DTO {
    private String sender;
    private String message;
}
