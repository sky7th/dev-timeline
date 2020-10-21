package com.sky7th.devtimeline.chat.inMemory.chatUser.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ChatUserResponseDto implements Serializable {

    private Long id;
    private String name;
    private String imageUrl;
}
