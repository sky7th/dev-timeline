package com.sky7th.devtimeline.chat.inMemory.chatRoom.domain;

import com.sky7th.devtimeline.chat.domain.chattingRoom.domain.ChattingRoom;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@NoArgsConstructor
@Getter
@RedisHash(value = "chat_room")
public class ChatRoom implements Serializable {

    @Id
    private String id;
    @Indexed
    private Long roomId;
    private String name;

    public ChatRoom(String id, Long roomId, String name) {
        this.id = id;
        this.roomId = roomId;
        this.name = name;
    }

    public static ChatRoom toEntity(ChattingRoom chattingRoom) {
        return new ChatRoom(chattingRoom.getRoomId(), chattingRoom.getId(), chattingRoom.getName());
    }
}