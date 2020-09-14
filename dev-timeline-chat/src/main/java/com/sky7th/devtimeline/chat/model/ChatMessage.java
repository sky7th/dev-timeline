package com.sky7th.devtimeline.chat.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;

@NoArgsConstructor
@Getter
@Setter
public class ChatMessage {

    public static final SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");

    public enum MessageType {
        ENTER, TALK, QUIT
    }

    private MessageType type;
    private String roomId;
    private ChatSender sender;
    private String message;
    private long userCount;
    private String createdDate;

    @Builder
    public ChatMessage(MessageType type, String roomId, ChatSender sender, String message, long userCount) {
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
        this.userCount = userCount;
    }

}