package com.sky7th.devtimeline.chat.config.handler;

import static org.springframework.messaging.simp.stomp.StompCommand.DISCONNECT;
import static org.springframework.messaging.simp.stomp.StompCommand.SUBSCRIBE;
import static org.springframework.messaging.simp.stomp.StompCommand.UNSUBSCRIBE;

import com.sky7th.devtimeline.chat.config.security.TokenValidator;
import com.sky7th.devtimeline.chat.service.ChatUserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final TokenValidator tokenProvider;
    private final ChatUserService chatUserService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        final StompCommand command = accessor.getCommand();
        String token = getJwtFromAccessor(accessor);
        Claims claims = tokenProvider.validateToken(token);
        Long userId = Long.parseLong(claims.getId());
        String roomId = accessor.getFirstNativeHeader("id");

        if (command == SUBSCRIBE) {
            chatUserService.enter(roomId, userId, claims);

        } else if (command == UNSUBSCRIBE) {
            chatUserService.exit(roomId, userId);

        } else if (command == DISCONNECT) {
            chatUserService.disconnect(userId);
        }

        return message;
    }

    private String getJwtFromAccessor(StompHeaderAccessor accessor) {
        String tokenRequestHeaderPrefix = "Bearer ";
        String bearerToken = accessor.getFirstNativeHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(tokenRequestHeaderPrefix)) {
            return bearerToken.replace(tokenRequestHeaderPrefix, "");
        }
        return null;
    }
}