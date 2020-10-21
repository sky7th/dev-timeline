package com.sky7th.devtimeline.api.config.handler;

import static org.springframework.messaging.simp.stomp.StompCommand.CONNECT;
import static org.springframework.messaging.simp.stomp.StompCommand.DISCONNECT;
import static org.springframework.messaging.simp.stomp.StompCommand.SEND;
import static org.springframework.messaging.simp.stomp.StompCommand.SUBSCRIBE;
import static org.springframework.messaging.simp.stomp.StompCommand.UNSUBSCRIBE;

import com.sky7th.devtimeline.api.security.TokenProvider;
import com.sky7th.devtimeline.chat.domain.chattingMessage.domain.ChattingMessage.MessageType;
import com.sky7th.devtimeline.chat.inMemory.chatMessage.domain.ChatMessage;
import com.sky7th.devtimeline.chat.inMemory.chatMessage.event.OnGeneratePushMessageEvent;
import com.sky7th.devtimeline.chat.inMemory.chatMessage.service.ChatMessageService;
import com.sky7th.devtimeline.chat.inMemory.chatRoom.service.ChatRoomService;
import com.sky7th.devtimeline.chat.inMemory.chatSession.domain.ChatSession;
import com.sky7th.devtimeline.chat.inMemory.chatSession.service.ChatSessionService;
import com.sky7th.devtimeline.chat.inMemory.chatUser.domain.ChatUser;
import com.sky7th.devtimeline.chat.inMemory.chatUser.service.ChatUserService;
import com.sky7th.devtimeline.user.dto.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
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

    private static final String SUBSCRIBE_ID = "id";

    private final TokenProvider tokenProvider;
    private final ChatRoomService chatRoomService;
    private final ChatUserService chatUserService;
    private final ChatMessageService chatMessageService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ChatSessionService chatSessionService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        final StompCommand command = accessor.getCommand();
        String token = getJwtFromAccessor(accessor);

        if (isNormalDisconnect(token, command) || command == SUBSCRIBE) {
            return message;
        }

        String sessionId = accessor.getSessionId();

        if (isFinalDisconnect(token, command)) {
            ChatUser chatUser = chatUserService.findBySessionId(sessionId);
            chatUser.getChatRoomIds().forEach(_roomId -> {
                pushMessage(command, _roomId, chatUser);
            });
            chatUserService.delete(sessionId);
            return message;
        }

        if (!isLogined(token)) {
            return message;
        }

        if (command == CONNECT) {
            UserContext userContext = tokenProvider.getUserContextFromToken(token);
            chatUserService.save(sessionId, userContext);
            return message;
        }

        String roomIdStr = accessor.getFirstNativeHeader(SUBSCRIBE_ID);
        Long roomId = roomIdStr == null ? null : Long.parseLong(roomIdStr);
        String type = accessor.getFirstNativeHeader("type");
        ChatUser chatUser = chatUserService.findBySessionId(sessionId);

        if (command == UNSUBSCRIBE || isUserEnter(type, command)) {
            pushMessage(command, roomId, chatUser);
        }

        return message;
    }

    private boolean isFinalDisconnect(String token, StompCommand command) {
        return !isLogined(token) && command == DISCONNECT;
    }

    private boolean isNormalDisconnect(String token, StompCommand command) {
        return isLogined(token) && command == DISCONNECT;
    }

    private boolean isLogined(String token) {
        return token != null && !token.equals("null");
    }

    private boolean isUserEnter(String type, StompCommand command) {
        return type != null && type.equals(MessageType.ENTER.name()) && command == SEND;
    }

    private boolean isOpenedMultipleBrowser(StompCommand command, ChatSession chatSession) {
        if (command == SEND) {
            return chatSession.getSessionIds().size() > 1;
        }
        else {
            return chatSession.getSessionIds().size() != 0;
        }
    }

    private String getJwtFromAccessor(StompHeaderAccessor accessor) {
        String tokenRequestHeaderPrefix = "Bearer ";
        String bearerToken = accessor.getFirstNativeHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(tokenRequestHeaderPrefix)) {
            return bearerToken.replace(tokenRequestHeaderPrefix, "");
        }
        return null;
    }

    private void pushMessage(StompCommand command, Long roomId, ChatUser chatUser) {
        int userCount;
        ChatSession chatSession;
        ChatMessage chatMessage;

        if (command == UNSUBSCRIBE || command == DISCONNECT) {
            chatSession = chatRoomService.exit(roomId, chatUser);
            userCount = chatSessionService.countByRoomId(roomId);
            chatMessage = ChatMessage.exitMessage(chatUser, roomId, userCount);
        } else {
            chatSession = chatRoomService.enter(roomId, chatUser);
            userCount = chatSessionService.countByRoomId(roomId);
            chatMessage = ChatMessage.enterMessage(chatUser, roomId, userCount);
        }

        if (isOpenedMultipleBrowser(command, chatSession)) {
            chatMessage.setType(MessageType.MULTIPLE);
        }
        applicationEventPublisher.publishEvent(new OnGeneratePushMessageEvent(chatMessageService.save(chatMessage)));
    }
}