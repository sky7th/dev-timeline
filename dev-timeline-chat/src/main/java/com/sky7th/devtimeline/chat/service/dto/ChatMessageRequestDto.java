package com.sky7th.devtimeline.chat.service.dto;

import com.sky7th.devtimeline.chat.model.ChatMessage;
import com.sky7th.devtimeline.chat.model.ChatRoom;
import com.sky7th.devtimeline.chat.model.ChatUser;
import com.sky7th.devtimeline.core.domain.chattingMessage.domain.ChattingMessage.MessageType;
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
    private String roomId;
    private ChatUser sender;
    private String message;

    public static ChatMessage toTalkMessageEntity(ChatMessageRequestDto requestDto, ChatRoom chatRoom) {
        return ChatMessage.builder()
            .type(requestDto.getType())
            .roomId(requestDto.getRoomId())
            .userCount(chatRoom.getUserCount())
            .sender(requestDto.getSender())
            .message(requestDto.getMessage())
            .createdDate(LocalDateTimeUtils.toStringNowUntilMilisecond())
            .build();
    }
}
