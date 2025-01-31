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
public class ChatParticipant02 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 소속된 단톡방
    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom02 room;

    // 참석자 이름
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User01 chatter;

    // 입장시간
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
    private LocalDateTime enterAt;

    // 퇴장시간
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime exitAt;

    // 참여정도
//    @Enumerated(EnumType.ORDINAL)
//    private ChatterAttendance attendance;

    // 상태메시지
//    private String statusMessage;

    /*
    나갔다 들어오는 경우, 아직 고려 안함
     */
}
