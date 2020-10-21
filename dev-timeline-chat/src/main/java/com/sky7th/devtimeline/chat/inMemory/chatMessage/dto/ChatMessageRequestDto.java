package com.sky7th.devtimeline.chat.inMemory.chatMessage.dto;

import com.sky7th.devtimeline.chat.inMemory.chatMessage.domain.ChatMessage;
import com.sky7th.devtimeline.chat.inMemory.chatUser.domain.ChatUser;
import com.sky7th.devtimeline.chat.domain.chattingMessage.domain.ChattingMessage.MessageType;
import com.sky7th.devtimeline.core.utils.LocalDateTimeUtils;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ChatMessageRequestDto implements Serializable {

    private MessageType type;
    private Long roomId;
    private ChatUser sender;
    private String message;

    public static ChatMessage toTalkMessageEntity(ChatMessageRequestDto requestDto, int userCount) {
        return ChatMessage.builder()
            .type(requestDto.getType())
            .roomId(requestDto.getRoomId())
            .userCount(userCount)
            .sender(requestDto.getSender())
            .message(requestDto.getMessage())
            .createdDate(LocalDateTimeUtils.toStringNowUntilMilisecond())
            .build();
    }
}
