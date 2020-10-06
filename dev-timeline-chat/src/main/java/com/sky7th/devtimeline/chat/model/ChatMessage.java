package com.sky7th.devtimeline.chat.model;

import com.sky7th.devtimeline.core.domain.chattingMessage.domain.ChattingMessage;
import com.sky7th.devtimeline.core.domain.chattingMessage.domain.ChattingMessage.MessageType;
import com.sky7th.devtimeline.core.domain.user.domain.User;
import com.sky7th.devtimeline.core.utils.LocalDateTimeUtils;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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
    private String roomId;
    private Long realRoomId;
    private int userCount;
    private ChatUser sender;
    private String message;
    private String createdDate;

    @Builder
    public ChatMessage(String id, MessageType type, String roomId, Long realRoomId, int userCount, ChatUser sender, String message, String createdDate) {
        this.id = id;
        this.type = type;
        this.roomId = roomId;
        this.realRoomId = realRoomId;
        this.userCount = userCount;
        this.sender = sender;
        this.message = message;
        this.createdDate = createdDate;
    }

    public static ChatMessage enterMessage(ChatUser chatUser, ChatRoom chatRoom) {
        return ChatMessage.builder()
            .type(MessageType.ENTER)
            .roomId(chatRoom.getId())
            .realRoomId(chatRoom.getRoomId())
            .userCount(chatRoom.getUserCount())
            .message(chatUser.getName() + " 님이 들어왔습니다.")
            .createdDate(LocalDateTimeUtils.toStringNowUntilMilisecond())
            .build();
    }

    public static ChatMessage exitMessage(ChatUser chatUser, ChatRoom chatRoom) {
        return ChatMessage.builder()
            .type(MessageType.QUIT)
            .roomId(chatRoom.getId())
            .realRoomId(chatRoom.getRoomId())
            .userCount(chatRoom.getUserCount())
            .message(chatUser.getName() + " 님이 나갔습니다.")
            .createdDate(LocalDateTimeUtils.toStringNowUntilMilisecond())
            .build();
    }

    public static ChattingMessage from(ChatMessage entity) {
        ChatUser chatUser = entity.getSender();

        return ChattingMessage.builder()
            .messageId(entity.getId())
            .type(entity.getType())
            .roomId(entity.getRealRoomId())
            .userCount(entity.getUserCount())
            .message(entity.getMessage())
            .user(chatUser == null ? null : new User(chatUser.getUserId()))
            .createdDate(LocalDateTimeUtils.toLocalDateTimeForMilisecond(entity.getCreatedDate()))
            .build();
    }
}