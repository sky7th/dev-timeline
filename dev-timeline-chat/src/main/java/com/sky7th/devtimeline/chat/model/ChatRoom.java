package com.sky7th.devtimeline.chat.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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
    private Set<Long> chatUserIds = new HashSet<>();

    public ChatRoom(String name) {
        this(null, name);
    }

    public ChatRoom(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getUserCount() {
        return this.chatUserIds.size();
    }
}