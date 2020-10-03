package com.sky7th.devtimeline.chat.config.handler;

import static org.springframework.messaging.simp.stomp.StompCommand.CONNECT;
import static org.springframework.messaging.simp.stomp.StompCommand.DISCONNECT;
import static org.springframework.messaging.simp.stomp.StompCommand.SUBSCRIBE;
import static org.springframework.messaging.simp.stomp.StompCommand.UNSUBSCRIBE;

import com.sky7th.devtimeline.chat.config.security.TokenValidator;
import com.sky7th.devtimeline.chat.config.security.UserContext;
import com.sky7th.devtimeline.chat.model.ChatMessage;
import com.sky7th.devtimeline.chat.model.ChatMessage.MessageType;
import com.sky7th.devtimeline.chat.model.ChatRoom;
import com.sky7th.devtimeline.chat.service.ChatMessageService;
import com.sky7th.devtimeline.chat.service.ChatRoomService;
import com.sky7th.devtimeline.chat.service.ChatUserService;
import com.sky7th.devtimeline.chat.service.OnGeneratePushMessageEvent;
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

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        final StompCommand command = accessor.getCommand();
        String sessionId = accessor.getSessionId();
        String token = getJwtFromAccessor(accessor);

        if (isDisconnectedByClosingTheBrowser(token, command)) {
            chatRoomService.exitAllChatRoomBySessionId(sessionId);
            return message;
        }

        UserContext userContext = tokenProvider.getUserContextFromToken(token);
        String roomId = accessor.getFirstNativeHeader(SUBSCRIBE_ID);

        if (command == CONNECT) {
            chatUserService.save(sessionId, userContext);
        }

        ChatRoom chatRoom = null;
        ChatMessage chatMessage = null;

        if (command == SUBSCRIBE) {
            chatRoom = chatRoomService.enter(roomId, sessionId, userContext.getId());
            chatMessage = ChatMessage.enterMessage(userContext, chatRoom);
        }

        if (command == UNSUBSCRIBE) {
            chatRoom = chatRoomService.exit(roomId, sessionId, userContext.getId());
            chatMessage = ChatMessage.exitMessage(userContext, chatRoom);
        }

        if ((command == SUBSCRIBE || command == UNSUBSCRIBE)) {
            if (isOpenedMultipleBrowser(chatRoom, userContext)) {
                chatMessage.setType(MessageType.MULTIPLE);
            }
            pushMessage(chatMessageService.save(chatMessage));
        }

        return message;
    }

    private boolean isDisconnectedByClosingTheBrowser(String token, StompCommand command) {
        return token == null && command == DISCONNECT;
    }

    private boolean isOpenedMultipleBrowser(ChatRoom chatRoom, UserContext userContext) {
        return chatRoom.getChatUserSessionCountMap().get(userContext.getId()) != 1;
    }

    private String getJwtFromAccessor(StompHeaderAccessor accessor) {
        String tokenRequestHeaderPrefix = "Bearer ";
        String bearerToken = accessor.getFirstNativeHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(tokenRequestHeaderPrefix)) {
            return bearerToken.replace(tokenRequestHeaderPrefix, "");
        }
        return null;
    }

    private void pushMessage(ChatMessage chatMessage) {
        OnGeneratePushMessageEvent onGenerateEmailVerificationEvent = new OnGeneratePushMessageEvent(chatMessage);
        applicationEventPublisher.publishEvent(onGenerateEmailVerificationEvent);
    }
}