package com.sky7th.devtimeline.core.domain.user.dto;

import static com.sky7th.devtimeline.core.utils.LocalDateTimeUtils.toStringDate;

import com.sky7th.devtimeline.core.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class UserDetailResponseDto {

    private Long id;
    private String name;
    private String email;
    private String imageUrl;
    private String createdDate;

    public static UserDetailResponseDto of(User entity) {
        return UserDetailResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .email(entity.getEmail())
            .imageUrl(entity.getImageUrl())
            .createdDate(toStringDate(entity.getCreatedDate(), "yyyy-MM-dd"))
            .build();
    }
}
