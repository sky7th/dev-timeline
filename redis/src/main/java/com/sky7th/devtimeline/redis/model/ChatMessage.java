package com.sky7th.devtimeline.redis.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ChatMessage {

    public enum MessageType {
        ENTER, TALK, QUIT
    }

    private MessageType type;
    private String roomId;
    private ChatSender sender;
    private String message;
    private long userCount;

    @Builder
    public ChatMessage(MessageType type, String roomId, ChatSender sender, String message, long userCount) {
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
        this.userCount = userCount;
    }

}