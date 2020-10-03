package com.sky7th.devtimeline.chat.model;

import com.sky7th.devtimeline.chat.config.security.UserContext;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class ChatMessage implements Serializable {

    public static final SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");

    public enum MessageType {
        ENTER, TALK, QUIT, MULTIPLE
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
    public ChatMessage(String id, MessageType type, String roomId, int userCount, ChatUser sender, String message, String createdDate) {
        this.id = id;
        this.type = type;
        this.roomId = roomId;
        this.userCount = userCount;
        this.sender = sender;
        this.message = message;
        this.createdDate = createdDate;
    }

    public static ChatMessage enterMessage(UserContext userContext, ChatRoom chatRoom) {
        return ChatMessage.builder()
            .type(MessageType.ENTER)
            .roomId(chatRoom.getId())
            .userCount(chatRoom.getUserCount())
            .message(userContext.getName() + " 님이 들어왔습니다.")
            .createdDate(ChatMessage.format.format(new Date()))
            .build();
    }

    public static ChatMessage exitMessage(UserContext userContext, ChatRoom chatRoom) {
        return ChatMessage.builder()
            .type(MessageType.QUIT)
            .roomId(chatRoom.getId())
            .userCount(chatRoom.getUserCount())
            .message(userContext.getName() + " 님이 나갔습니다.")
            .createdDate(ChatMessage.format.format(new Date()))
            .build();
    }
}