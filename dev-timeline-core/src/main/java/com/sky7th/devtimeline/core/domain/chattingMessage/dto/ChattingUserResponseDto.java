package com.sky7th.devtimeline.core.domain.chattingMessage.dto;

import com.sky7th.devtimeline.core.domain.user.domain.User;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ChattingUserResponseDto implements Serializable {

    private Long userId;
    private String name;
    private String imageUrl;

    public static ChattingUserResponseDto of(User entity) {
        return new ChattingUserResponseDto(entity.getId(), entity.getName(), entity.getImageUrl());
    }
}
