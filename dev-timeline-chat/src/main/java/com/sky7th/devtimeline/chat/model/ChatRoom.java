package com.sky7th.devtimeline.chat.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@NoArgsConstructor
@Getter
@RedisHash(value = "chat_room")
public class ChatRoom implements Serializable {

    @Id
    private String id;
    private String name;
    private Map<Long, Integer> chatUserSessionCountMap = new HashMap<>();

    public ChatRoom(String name) {
        this(null, name);
    }

    public ChatRoom(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getUserCount() {
        return this.chatUserSessionCountMap.size();
    }
}