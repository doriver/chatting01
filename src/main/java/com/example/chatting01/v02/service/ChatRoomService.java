package com.example.chatting01.v02.service;

import com.example.chatting01.v01.entity.Chatter01;
import com.example.chatting01.v01.entity.GroupChatRoom01;
import com.example.chatting01.v01.entity.User01;
import com.example.chatting01.v01.repository.Chatter01Repository;
import com.example.chatting01.v01.repository.GroupChatRoom01Repository;
import com.example.chatting01.v01.repository.User01Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired private User01Repository user01Repository;
    @Autowired private GroupChatRoom01Repository groupChatRoom01Repository;
    @Autowired private Chatter01Repository chatter01Repository;

    /*
        개설자가 채팅방 종료
     */
    public void mentorEndRoom(long roomId) {
        GroupChatRoom01 chatRoom = groupChatRoom01Repository.findById(roomId).orElse(null);
        LocalDateTime endTime = LocalDateTime.now();

        // 채팅방 종료시간 입력
        chatRoom.setEndedAt(endTime);
        groupChatRoom01Repository.save(chatRoom);

        // 채팅방 참석자들 나가는 시간 입력
        List<Chatter01> chatterList = chatter01Repository.findAllByRoomAndExitTime(chatRoom, null);
        for (Chatter01 chatter : chatterList) {
            chatter.setExitTime(endTime);
        }
        chatter01Repository.saveAll(chatterList);

        // 채팅방에 연결된 웹소켓 통신 종료시키기
    }

    /*
        채팅방 참석자가, 해당방 나가는 기능
     */
    public void chatterExitRoom(long roomId, long chatterId) {
        GroupChatRoom01 chatRoom = groupChatRoom01Repository.findById(roomId).orElse(null);
        User01 participant = user01Repository.findById(chatterId).orElse(null);

        if (participant != null && chatRoom != null) {
            Optional<Chatter01> attendance = chatter01Repository.findByChatterAndRoomAndExitTime(participant, chatRoom, null);
            if (!attendance.isEmpty()) {
                Chatter01 chatter01 = attendance.get();
                chatter01.setExitTime(LocalDateTime.now()); // 여기까지하면 update될줄 알았는데, 안됨
                chatter01Repository.save(chatter01);
            }
        }
    }
    
    /*
        사용자가 단톡방 입장하기
     */
    public void userEnterRoom(long rid, long uid) {
        // 일단은 단톡방으로 무조건 넘어가는 로직만 있음
        // 여러가지 경우를 고려해야할수도( 단톡방 없는경우 등 )
        GroupChatRoom01 chatRoom = groupChatRoom01Repository.findById(rid).orElse(null);
        User01 participant = user01Repository.findById(uid).orElse(null);
        // 로그인 여부까지 체크하면 좋음

        Optional<Chatter01> attendance = chatter01Repository.findByChatterAndRoomAndExitTime(participant, chatRoom, null);
        if (attendance.isEmpty()) {
            if (participant != null && chatRoom != null) {
                Chatter01 chatter = Chatter01.builder()
                        .room(chatRoom).chatter(participant)
                        .build();
                chatter01Repository.save(chatter);
            } else {
                // throw new Exception();
            }
        }
    }

    /*
        아직 종료안된 단체톡방들 select
     */
    public List<GroupChatRoom01> getChatRoomList() {
        List<GroupChatRoom01> roomList = groupChatRoom01Repository.findAllByEndedAt(null);
        return roomList;
    }


    /*
        멘토가 단톡방 생성
     */
    public void mentorCreateRoom(long mid, String roomName, int userLimit) {

        User01 mentor = user01Repository.findById(mid).orElse(null);

        if (mentor != null && mentor.getRole().name() == "MENTOR") {
            GroupChatRoom01 room = GroupChatRoom01.builder()
                    .mentor(mentor).roomName(roomName).limitNumber(userLimit)
                    .build();
            groupChatRoom01Repository.save(room);
        } else {
            // throw new Exception();
        }
    }


}
