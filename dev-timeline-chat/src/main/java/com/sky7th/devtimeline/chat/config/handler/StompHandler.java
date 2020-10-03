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
import com.sky7th.devtimeline.chat.model.ChatUser;
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

        if (command == CONNECT) {
            UserContext userContext = tokenProvider.getUserContextFromToken(token);
            chatUserService.save(sessionId, userContext);
            return message;
        }

        if (isFinalDisconnect(token, command)) {
            ChatUser chatUser = chatUserService.findBySessionId(sessionId);
            chatUser.getChatRoomIds().forEach(_roomId -> {
                ChatRoom _chatRoom = chatRoomService.exit(_roomId, chatUser);
                ChatMessage _chatMessage = ChatMessage.exitMessage(chatUser, _chatRoom);
                pushMessage(chatMessageService.save(_chatMessage), command, _chatRoom, chatUser);
            });

            chatUserService.delete(sessionId);

            return message;
        }

        if (command == SUBSCRIBE || command == UNSUBSCRIBE) {
            ChatUser chatUser = chatUserService.findBySessionId(sessionId);
            String roomId = accessor.getFirstNativeHeader(SUBSCRIBE_ID);
            ChatRoom chatRoom;
            ChatMessage chatMessage;

            if (command == SUBSCRIBE) {
                chatRoom = chatRoomService.enter(roomId, chatUser);
                chatMessage = ChatMessage.enterMessage(chatUser, chatRoom);
            }
            else {
                chatRoom = chatRoomService.exit(roomId, chatUser);
                chatMessage = ChatMessage.exitMessage(chatUser, chatRoom);
            }

            if (isOpenedMultipleBrowser(command, chatRoom, chatUser)) {
                chatMessage.setType(MessageType.MULTIPLE);
            }

            pushMessage(chatMessageService.save(chatMessage), command, chatRoom, chatUser);
        }

        return message;
    }

    private boolean isFinalDisconnect(String token, StompCommand command) {
        return token == null && command == DISCONNECT;
    }

    private boolean isOpenedMultipleBrowser(StompCommand command, ChatRoom chatRoom, ChatUser chatUser) {
        if (command == SUBSCRIBE) {
            return chatRoom.getChatUserSessionCountMap().get(chatUser.getUserId()) != 1;
        }
        else {
            return chatRoom.getChatUserSessionCountMap().containsKey(chatUser.getUserId());
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

    private void pushMessage(ChatMessage chatMessage, StompCommand command, ChatRoom chatRoom, ChatUser chatUser) {
        if (isOpenedMultipleBrowser(command, chatRoom, chatUser)) {
            chatMessage.setType(MessageType.MULTIPLE);
        }
        OnGeneratePushMessageEvent onGenerateEmailVerificationEvent = new OnGeneratePushMessageEvent(chatMessage);
        applicationEventPublisher.publishEvent(onGenerateEmailVerificationEvent);
    }
}