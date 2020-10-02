package com.sky7th.devtimeline.chat.service.dto;

import com.sky7th.devtimeline.chat.model.ChatMessage;
import com.sky7th.devtimeline.chat.model.ChatMessage.ChatMessageBuilder;
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

    public static ChatMessageBuilder chatMessageBuilder(ChatMessageRequestDto requestDto, int userCount) {
        return ChatMessage.builder()
            .type(requestDto.getType())
            .roomId(requestDto.getRoomId())
            .userCount(userCount)
            .createdDate(ChatMessage.format.format(new Date()));
    }

    public static ChatMessage toEnterMessageEntity(ChatMessageRequestDto requestDto, int updatedUserCount) {
        return chatMessageBuilder(requestDto, updatedUserCount)
            .sender(ChatUser.builder().name("NOTICE").build())
            .message(requestDto.getSender().getName() + " 님이 입장했습니다.")
            .build();
    }

    public static ChatMessage toExitMessageEntity(ChatMessageRequestDto requestDto, int updatedUserCount) {
        return chatMessageBuilder(requestDto, updatedUserCount)
            .sender(ChatUser.builder().name("NOTICE").build())
            .message(requestDto.getSender().getName() + " 님이 나갔습니다.")
            .build();
    }

    public static ChatMessage toTalkMessageEntity(ChatMessageRequestDto requestDto, int userCount) {
        return chatMessageBuilder(requestDto, userCount)
            .sender(requestDto.getSender())
            .message(requestDto.getMessage())
            .build();
    }
}
