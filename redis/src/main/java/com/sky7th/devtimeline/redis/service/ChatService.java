package com.sky7th.devtimeline.redis.service;

import com.sky7th.devtimeline.redis.model.ChatMessage;
import com.sky7th.devtimeline.redis.model.ChatSender;
import com.sky7th.devtimeline.redis.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;
    private final ChatRoomRepository chatRoomRepository;

    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }

    public void sendChatMessage(ChatMessage chatMessage) {
        chatMessage.setUserCount(chatRoomRepository.getUserCount(chatMessage.getRoomId()));
        ChatSender noticeSender = ChatSender.builder().name("NOTICE").build();

        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getSender().getName() + "님이 입장했습니다.");
            chatMessage.setSender(noticeSender);
        } else if (ChatMessage.MessageType.QUIT.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getSender().getName() + "님이 나갔습니다.");
            chatMessage.setSender(noticeSender);
        }
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
    }
}