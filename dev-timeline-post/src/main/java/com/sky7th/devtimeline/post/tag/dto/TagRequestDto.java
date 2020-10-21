package com.sky7th.devtimeline.post.tag.dto;

import com.sky7th.devtimeline.post.tag.domain.Tag;
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

    public static Tag toEntity(TagRequestDto requestDto, Long userId) {
        return Tag.builder()
            .id(requestDto.getId())
            .userId(userId)
            .name(requestDto.getName())
            .build();
    }

    public static List<Tag> toEntities(List<TagRequestDto> requestDtos, Long userId) {
        return requestDtos.stream()
            .map(requestDto -> TagRequestDto.toEntity(requestDto, userId))
            .collect(Collectors.toList());
    }
}
