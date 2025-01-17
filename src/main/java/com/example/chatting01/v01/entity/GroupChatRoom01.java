package com.example.chatting01.v01.entity;

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
public class GroupChatRoom01 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roomid;

    // 단톡방 개설자
    @ManyToOne
    @JoinColumn(name = "uid")
    private User01 mentor;

    // 단톡방 이름
    private String roomName;

    // 참여 제한 인원
    private int limitNumber;

    // 생성 시간
    private LocalDateTime createdAt;

    // 종료 시간
    private LocalDateTime endedAt;

    // 채팅방에서 에러 발생시, 관련 메시지 담을곳
    private String errorMessage;

    /*
    삭제할떄 문제 될수도
     */
    
    /* 단톡방 참석자들
     */

    /* 단톡방 채팅 메시지
     */

}
