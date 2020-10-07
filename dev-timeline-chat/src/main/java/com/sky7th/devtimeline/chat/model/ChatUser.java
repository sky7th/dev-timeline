package com.sky7th.devtimeline.chat.model;

import com.sky7th.devtimeline.chat.config.security.UserContext;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@NoArgsConstructor
@Getter
@RedisHash(value = "chat_user")
public class ChatUser implements Serializable {

    @Id
    private String sessionId;
    private Long userId;
    private String name;
    private String imageUrl;
    private Set<Long> chatRoomIds = new HashSet<>();

    @Builder
    public ChatUser(String sessionId, Long userId, String name, String imageUrl) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public static ChatUser of(UserContext userContext, String sessionId) {
        return new ChatUser(sessionId, userContext.getId(), userContext.getName(), userContext.getImageUrl());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChatUser that = (ChatUser) o;
        return Objects.equals(sessionId, that.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId);
    }
}
