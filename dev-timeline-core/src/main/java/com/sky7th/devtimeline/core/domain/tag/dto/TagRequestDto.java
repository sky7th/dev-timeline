package com.sky7th.devtimeline.core.domain.tag.dto;

import com.sky7th.devtimeline.core.domain.tag.domain.Tag;
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

    public static Tag toEntity(TagRequestDto requestDto) {
        return Tag.builder()
            .id(requestDto.getId())
            .name(requestDto.getName())
            .build();
    }

    public static List<Tag> toEntities(List<TagRequestDto> requestDtos) {
        return requestDtos.stream().map(TagRequestDto::toEntity).collect(Collectors.toList());
    }
}
