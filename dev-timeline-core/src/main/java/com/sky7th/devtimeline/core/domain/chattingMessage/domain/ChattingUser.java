package com.sky7th.devtimeline.core.domain.chattingMessage.domain;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class ChattingUser {

    private Long userId;
    private String name;
    private String imageUrl;

    public ChattingUser(Long userId, String name, String imageUrl) {
        this.userId = userId;
        this.name = name;
        this.imageUrl = imageUrl;
    }
}