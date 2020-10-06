package com.sky7th.devtimeline.chat.model;

import com.sky7th.devtimeline.core.domain.chattingRoom.domain.ChattingRoom;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
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
    private Map<Long, Integer> chatUserSessionCountMap = new HashMap<>();

    public ChatRoom(String id, Long roomId, String name, Map<Long, Integer> chatUserSessionCountMap) {
        this.id = id;
        this.roomId = roomId;
        this.name = name;
        this.chatUserSessionCountMap = chatUserSessionCountMap;
    }

    public int getUserCount() {
        return this.chatUserSessionCountMap.size();
    }

    public static ChatRoom toEntity(ChattingRoom chattingRoom, Map<Long, Integer> chatUserSessionCountMap) {
        return new ChatRoom(null, chattingRoom.getId(), chattingRoom.getName(), chatUserSessionCountMap);
    }

    public static ChatRoom toEntity(String id, ChattingRoom chattingRoom, Map<Long, Integer> chatUserSessionCountMap) {
        return new ChatRoom(id, chattingRoom.getId(), chattingRoom.getName(), chatUserSessionCountMap);
    }
}