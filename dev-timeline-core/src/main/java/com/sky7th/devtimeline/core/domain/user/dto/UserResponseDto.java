package com.sky7th.devtimeline.core.domain.user.dto;

import com.sky7th.devtimeline.core.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
    private String imageUrl;

    public static UserResponseDto of(User entity) {
        return UserResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .email(entity.getEmail())
            .imageUrl(entity.getImageUrl())
            .build();
    }
}
