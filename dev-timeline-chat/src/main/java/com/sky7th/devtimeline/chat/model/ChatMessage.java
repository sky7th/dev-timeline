package com.sky7th.devtimeline.chat.model;

import java.text.SimpleDateFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@NoArgsConstructor
@Getter
@Setter
@RedisHash(value = "chat_message")
public class ChatMessage {

    public static final SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");

    public enum MessageType {
        ENTER, TALK, QUIT
    }

    @Id
    private String id;
    private MessageType type;
    private String roomId;
    private int userCount;
    private ChatUser sender;
    private String message;
    private String createdDate;

    @Builder
    public ChatMessage(MessageType type, String roomId, int userCount, ChatUser sender, String message, String createdDate) {
        this.type = type;
        this.roomId = roomId;
        this.userCount = userCount;
        this.sender = sender;
        this.message = message;
        this.createdDate = createdDate;
    }

}