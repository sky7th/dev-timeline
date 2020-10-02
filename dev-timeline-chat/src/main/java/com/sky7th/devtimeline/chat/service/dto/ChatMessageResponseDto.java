package com.sky7th.devtimeline.chat.service.dto;

import com.sky7th.devtimeline.chat.model.ChatMessage.MessageType;
import com.sky7th.devtimeline.chat.model.ChatUser;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ChatMessageResponseDto implements Serializable {

    private String id;
    private MessageType type;
    private String roomId;
    private Long userCount;
    private ChatUser sender;
    private String message;
    private String createdDate;
}
