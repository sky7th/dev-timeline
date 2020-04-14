package com.sky7th.devtimeline.redis.controller;

import com.sky7th.devtimeline.redis.model.ChatMessage;
import com.sky7th.devtimeline.redis.repository.ChatRoomRepository;
import com.sky7th.devtimeline.redis.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        message.setSender(message.getSender());
        message.setUserCount(chatRoomRepository.getUserCount(message.getRoomId()));
        chatService.sendChatMessage(message);
    }
}