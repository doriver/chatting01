package com.example.chatting01.v01.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chatter01 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ctrid;

    // 소속된 단톡방
    private GroupChatRoom01 room;

    // 참석자 이름
    private User01 userName;

    // 입장시간
    private LocalDateTime enterTime;

    // 퇴장시간
    private LocalDateTime exitTime;

    // 참여정도
    private ChatterAttendance attendance;

    // 상태메시지
    private String statusMessage;

    /*
    나갔다 들어오는 경우, 아직 고려 안함
     */
}
