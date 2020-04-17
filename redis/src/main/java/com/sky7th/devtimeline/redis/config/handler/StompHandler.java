package com.sky7th.devtimeline.redis.config.handler;

import com.sky7th.devtimeline.redis.model.ChatMessage;
import com.sky7th.devtimeline.redis.model.ChatSender;
import com.sky7th.devtimeline.redis.repository.ChatRoomRepository;
import com.sky7th.devtimeline.redis.service.ChatService;
import com.sky7th.devtimeline.redis.service.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;
    private final TokenProvider tokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);


        if (StompCommand.CONNECT == accessor.getCommand()) {
            String token = accessor.getFirstNativeHeader("token");
            log.info("CONNECT {}", token);
            tokenProvider.validateToken(token);

        } else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            String roomId = chatService.getRoomId(Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId"));
            String sessionId = (String) message.getHeaders().get("simpSessionId");

            chatRoomRepository.setUserEnterInfo(sessionId, roomId);
            chatRoomRepository.plusUserCount(roomId);

//            String name = "aa";
//            chatService.sendChatMessage(ChatMessage.builder()
//                    .type(ChatMessage.MessageType.ENTER)
//                    .roomId(roomId)
//                    .sender(ChatSender.builder().name(name).build())
//                    .build());
            log.info("SUBSCRIBED {}, {}", sessionId, roomId);

        } else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            String sessionId = (String) message.getHeaders().get("simpSessionId");
            String roomId = chatRoomRepository.getUserEnterRoomId(sessionId);

            chatRoomRepository.minusUserCount(roomId);

            String name = "한 명이";
            chatService.sendChatMessage(ChatMessage.builder()
                    .type(ChatMessage.MessageType.QUIT)
                    .roomId(roomId)
                    .sender(ChatSender.builder().name(name).build())
                    .build());

            chatRoomRepository.removeUserEnterInfo(sessionId);
            log.info("DISCONNECTED {}, {}", sessionId, roomId);
        }
        return message;
    }

}