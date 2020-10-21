package com.sky7th.devtimeline.chat.inMemory.chatMessage.domain;

import com.sky7th.devtimeline.chat.domain.chattingMessage.domain.ChattingMessage;
import com.sky7th.devtimeline.chat.domain.chattingMessage.domain.ChattingMessage.MessageType;
import com.sky7th.devtimeline.chat.inMemory.chatUser.domain.ChatUser;
import com.sky7th.devtimeline.user.domain.User;
import com.sky7th.devtimeline.core.utils.LocalDateTimeUtils;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@NoArgsConstructor
@Getter
@Setter
@RedisHash(value = "chat_message")
public class ChatMessage implements Serializable {

    public static final SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");

    @Id
    private String id;
    private MessageType type;
    @Indexed
    private Long roomId;
    private int userCount;
    private ChatUser sender;
    private String message;
    private String createdDate;

    @Builder
    public ChatMessage(String id, MessageType type, Long roomId, int userCount, ChatUser sender, String message, String createdDate) {
        this.id = id;
        this.type = type;
        this.roomId = roomId;
        this.userCount = userCount;
        this.sender = sender;
        this.message = message;
        this.createdDate = createdDate;
    }

    public static ChatMessage enterMessage(ChatUser chatUser, Long roomId, int userCount) {
        return ChatMessage.builder()
            .type(MessageType.ENTER)
            .roomId(roomId)
            .userCount(userCount)
            .message(chatUser.getName() + " 님이 들어왔습니다.")
            .createdDate(LocalDateTimeUtils.toStringNowUntilMilisecond())
            .build();
    }

    public static ChatMessage exitMessage(ChatUser chatUser, Long roomId, int userCount) {
        return ChatMessage.builder()
            .type(MessageType.QUIT)
            .roomId(roomId)
            .userCount(userCount)
            .message(chatUser.getName() + " 님이 나갔습니다.")
            .createdDate(LocalDateTimeUtils.toStringNowUntilMilisecond())
            .build();
    }

    public static ChattingMessage from(ChatMessage entity) {
        ChatUser chatUser = entity.getSender();

        return ChattingMessage.builder()
            .messageId(entity.getId())
            .type(entity.getType())
            .roomId(entity.getRoomId())
            .userCount(entity.getUserCount())
            .message(entity.getMessage())
            .user(chatUser == null ? null : new User(chatUser.getUserId()))
            .createdDate(LocalDateTimeUtils.toLocalDateTimeForMilisecond(entity.getCreatedDate()))
            .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChatMessage that = (ChatMessage) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}