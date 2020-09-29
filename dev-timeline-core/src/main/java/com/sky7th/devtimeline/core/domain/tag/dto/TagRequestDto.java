package com.sky7th.devtimeline.core.domain.tag.dto;

import com.sky7th.devtimeline.core.domain.tag.domain.Tag;
import com.sky7th.devtimeline.core.domain.user.domain.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class TagRequestDto {

    private Long id;
    private String name;

    public static Tag toEntity(TagRequestDto requestDto, User user) {
        return Tag.builder()
            .id(requestDto.getId())
            .user(user)
            .name(requestDto.getName())
            .build();
    }

    public static List<Tag> toEntities(List<TagRequestDto> requestDtos, User user) {
        return requestDtos.stream()
            .map(requestDto -> TagRequestDto.toEntity(requestDto, user))
            .collect(Collectors.toList());
    }
}
