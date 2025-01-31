package com.example.chatting01.v02.service;

import com.example.chatting01.v02.entity.ChatMessage02;
import com.example.chatting01.v02.entity.ChatParticipant02;
import com.example.chatting01.v02.entity.ChatRoom02;
import com.example.chatting01.v02.entity.User01;
import com.example.chatting01.v02.repository.ChatMessage02Repository;
import com.example.chatting01.v02.repository.ChatParticipant02Repository;
import com.example.chatting01.v02.repository.ChatRoom02Repository;
import com.example.chatting01.v02.repository.User01Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageService {
    @Autowired private ChatRoom02Repository chatRoom02Repository;
    @Autowired private ChatParticipant02Repository chatParticipant02Repository;
    @Autowired private ChatMessage02Repository chatMessage02Repository;
    @Autowired private User01Repository user01Repository;

    /*
        채팅메시지 저장하기
     */
    public void saveMessage(long roomId, long senderId, String message) {
        User01 user01 = user01Repository.findById(senderId).orElse(null);
        ChatRoom02 chatRoom = chatRoom02Repository.findById(roomId).orElse(null);
        ChatParticipant02 chatParticipant02 = chatParticipant02Repository.findByChatterAndRoomAndExitTime(user01, chatRoom, null).orElse(null);

        if (chatParticipant02 != null && chatRoom != null) {
            ChatMessage02 dbChatMessage = ChatMessage02.builder()
                    .room(chatRoom).sender(chatParticipant02).message(message)
                    .build();
            chatMessage02Repository.save(dbChatMessage);
        }
    }

}
