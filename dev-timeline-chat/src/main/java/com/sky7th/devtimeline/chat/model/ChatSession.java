package com.sky7th.devtimeline.chat.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@NoArgsConstructor
@Getter
@RedisHash(value = "chat_session")
public class ChatSession implements Serializable {

    @Id
    private String id;
    @Indexed
    private Long roomId;
    @Indexed
    private Long userId;
    private Set<String> sessionIds = new HashSet<>();

    public ChatSession(Long roomId, Long userId) {
        this(null, roomId, userId);
    }

    @Builder
    public ChatSession(String id, Long roomId, Long userId) {
        this.id = id;
        this.roomId = roomId;
        this.userId = userId;
    }
}
