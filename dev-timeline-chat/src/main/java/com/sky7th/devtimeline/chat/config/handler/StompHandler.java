package com.sky7th.devtimeline.chat.config.handler;

import static org.springframework.messaging.simp.stomp.StompCommand.CONNECT;
import static org.springframework.messaging.simp.stomp.StompCommand.DISCONNECT;
import static org.springframework.messaging.simp.stomp.StompCommand.SUBSCRIBE;
import static org.springframework.messaging.simp.stomp.StompCommand.UNSUBSCRIBE;

import com.sky7th.devtimeline.chat.config.security.TokenValidator;
import com.sky7th.devtimeline.chat.config.security.UserContext;
import com.sky7th.devtimeline.chat.model.ChatMessage;
import com.sky7th.devtimeline.chat.model.ChatSession;
import com.sky7th.devtimeline.chat.model.ChatUser;
import com.sky7th.devtimeline.chat.service.ChatMessageService;
import com.sky7th.devtimeline.chat.service.ChatRoomService;
import com.sky7th.devtimeline.chat.service.ChatSessionService;
import com.sky7th.devtimeline.chat.service.ChatUserService;
import com.sky7th.devtimeline.chat.service.event.OnGeneratePushMessageEvent;
import com.sky7th.devtimeline.core.domain.chattingMessage.domain.ChattingMessage.MessageType;
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

    private final TokenValidator tokenProvider;
    private final ChatRoomService chatRoomService;
    private final ChatUserService chatUserService;
    private final ChatMessageService chatMessageService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ChatSessionService chatSessionService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        final StompCommand command = accessor.getCommand();
        String roomIdStr = accessor.getFirstNativeHeader(SUBSCRIBE_ID);
        Long roomId = roomIdStr == null ? null : Long.parseLong(roomIdStr);
        String sessionId = accessor.getSessionId();
        String token = getJwtFromAccessor(accessor);

        if (command == CONNECT) {
            UserContext userContext = tokenProvider.getUserContextFromToken(token);
            chatUserService.save(sessionId, userContext);
            return message;
        }

        if (isFinalDisconnect(token, command)) {
            ChatUser chatUser = chatUserService.findBySessionId(sessionId);
            chatUser.getChatRoomIds().forEach(_roomId -> {
                ChatSession chatSession = chatRoomService.exit(_roomId, chatUser);
                int userCount = chatSessionService.countByRoomId(_roomId);
                ChatMessage chatMessage = ChatMessage.exitMessage(chatUser, _roomId, userCount);
                pushMessage(chatMessageService.save(chatMessage), command, chatSession);
            });

            chatUserService.delete(sessionId);

            return message;
        }

        if (command == SUBSCRIBE || command == UNSUBSCRIBE) {
            ChatUser chatUser = chatUserService.findBySessionId(sessionId);

            if (command == SUBSCRIBE) {
                ChatSession chatSession = chatRoomService.enter(roomId, chatUser);
                int userCount = chatSessionService.countByRoomId(roomId);
                ChatMessage chatMessage = ChatMessage.enterMessage(chatUser, roomId, userCount);
                pushMessage(chatMessageService.save(chatMessage), command, chatSession);
            }
            else {
                ChatSession chatSession = chatRoomService.exit(roomId, chatUser);
                int userCount = chatSessionService.countByRoomId(roomId);
                ChatMessage chatMessage = ChatMessage.exitMessage(chatUser, roomId, userCount);
                pushMessage(chatMessageService.save(chatMessage), command, chatSession);
            }
        }

        return message;
    }

    private boolean isFinalDisconnect(String token, StompCommand command) {
        return token == null && command == DISCONNECT;
    }

    private boolean isOpenedMultipleBrowser(StompCommand command, ChatSession chatSession) {
        if (command == SUBSCRIBE) {
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

    private void pushMessage(ChatMessage chatMessage, StompCommand command, ChatSession chatSession) {
        if (isOpenedMultipleBrowser(command, chatSession)) {
            chatMessage.setType(MessageType.MULTIPLE);
        }
        applicationEventPublisher.publishEvent(new OnGeneratePushMessageEvent(chatMessage));
    }
}