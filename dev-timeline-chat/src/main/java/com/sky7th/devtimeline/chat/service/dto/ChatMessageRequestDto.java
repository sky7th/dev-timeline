package com.sky7th.devtimeline.chat.service.dto;

import com.sky7th.devtimeline.chat.model.ChatMessage;
import com.sky7th.devtimeline.chat.model.ChatMessage.MessageType;
import com.sky7th.devtimeline.chat.model.ChatUser;
import java.io.Serializable;
import java.util.Date;
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

    public static ChatMessage toTalkMessageEntity(ChatMessageRequestDto requestDto, int userCount) {
        return ChatMessage.builder()
            .type(requestDto.getType())
            .roomId(requestDto.getRoomId())
            .userCount(userCount)
            .sender(requestDto.getSender())
            .message(requestDto.getMessage())
            .createdDate(ChatMessage.format.format(new Date()))
            .build();
    }
}
