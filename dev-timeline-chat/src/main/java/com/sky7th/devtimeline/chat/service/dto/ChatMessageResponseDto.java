package com.sky7th.devtimeline.chat.service.dto;

import com.sky7th.devtimeline.chat.model.ChatMessage;
import com.sky7th.devtimeline.chat.model.ChatUser;
import com.sky7th.devtimeline.core.domain.chattingMessage.domain.ChattingMessage.MessageType;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ChatMessageResponseDto implements Serializable {

    private MessageType type;
    private int userCount;
    private ChatUser sender;
    private String message;
    private String createdDate;

    public static ChatMessageResponseDto of(ChatMessage entity) {
        return new ChatMessageResponseDto(entity.getType(), entity.getUserCount(),
            entity.getSender(), entity.getMessage(), entity.getCreatedDate());
    }

    public static List<ChatMessageResponseDto> of(List<ChatMessage> entities) {
        return entities.stream()
            .map(ChatMessageResponseDto::of)
            .collect(Collectors.toList());
    }
}
