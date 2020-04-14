package com.sky7th.devtimeline.redis.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type;
    private String roomId;
    private ChatSender Sender;
    private String message;

    @Override
    public String toString() {
        return "ChatMessage{" +
                "type=" + type +
                ", roomId='" + roomId + '\'' +
                ", Sender=" + Sender +
                ", message='" + message + '\'' +
                '}';
    }
}