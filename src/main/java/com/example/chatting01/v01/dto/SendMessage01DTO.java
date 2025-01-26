package com.example.chatting01.v01.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMessage01DTO {
    private String sender;
    private String message;
}
