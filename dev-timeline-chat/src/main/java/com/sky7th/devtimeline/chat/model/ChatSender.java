package com.sky7th.devtimeline.chat.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatSender {

    private String id;
    private String name;
    private String imageUrl;

    @Builder
    public ChatSender(String id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
