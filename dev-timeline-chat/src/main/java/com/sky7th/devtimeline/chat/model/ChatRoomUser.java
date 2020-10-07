package com.sky7th.devtimeline.chat.model;

import java.util.Objects;
import lombok.Getter;

@Getter
public class ChatRoomUser {

    private Long userId;
    private int sessionCount;

    public void increaseSessionCount() {
        this.sessionCount += 1;
    }

    public ChatRoomUser(Long userId) {
        this.userId = userId;
        this.sessionCount = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChatRoomUser that = (ChatRoomUser) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
