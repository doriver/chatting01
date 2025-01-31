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
public class Chatter01 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ctrid;

    // 소속된 단톡방
    @ManyToOne
    @JoinColumn(name = "roomid")
    private GroupChatRoom01 room;

    // 참석자 이름
    @ManyToOne
    @JoinColumn(name = "uid")
    private User01 perticipant;

    // 입장시간
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
    private LocalDateTime enterTime;

    // 퇴장시간
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime exitTime;

    // 참여정도
//    @Enumerated(EnumType.ORDINAL)
//    private ChatterAttendance attendance;

    // 상태메시지
//    private String statusMessage;

    /*
    나갔다 들어오는 경우, 아직 고려 안함
     */
}
