package com.sky7th.devtimeline.chat.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sky7th.devtimeline.chat.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String body = redisTemplate.getStringSerializer().deserialize(message.getBody());
            ChatMessage chatMessage = objectMapper.readValue(body, ChatMessage.class);
            messagingTemplate.convertAndSend("/sub/chat/rooms/" + chatMessage.getRoomId(), chatMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}