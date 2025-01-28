package com.example.chatting01.v02.service;

import com.example.chatting01.v01.entity.ChatMessage01;
import com.example.chatting01.v01.entity.Chatter01;
import com.example.chatting01.v01.entity.GroupChatRoom01;
import com.example.chatting01.v01.entity.User01;
import com.example.chatting01.v01.repository.ChatMessage01Repository;
import com.example.chatting01.v01.repository.Chatter01Repository;
import com.example.chatting01.v01.repository.GroupChatRoom01Repository;
import com.example.chatting01.v01.repository.User01Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageService {
    @Autowired private GroupChatRoom01Repository groupChatRoom01Repository;
    @Autowired private Chatter01Repository chatter01Repository;
    @Autowired private ChatMessage01Repository chatMessage01Repository;
    @Autowired private User01Repository user01Repository;

    /*
        채팅메시지 저장하기
     */
    public void saveMessage(long roomId, long senderId, String message) {
        User01 user01 = user01Repository.findById(senderId).orElse(null);
        GroupChatRoom01 chatRoom = groupChatRoom01Repository.findById(roomId).orElse(null);
        Chatter01 chatter01 = chatter01Repository.findByChatterAndRoom(user01, chatRoom).orElse(null);

        if (chatter01 != null && chatRoom != null) {
            ChatMessage01 dbChatMessage = ChatMessage01.builder()
                    .room(chatRoom).sender(chatter01).message(message)
                    .build();
            chatMessage01Repository.save(dbChatMessage);
        }
    }

}
