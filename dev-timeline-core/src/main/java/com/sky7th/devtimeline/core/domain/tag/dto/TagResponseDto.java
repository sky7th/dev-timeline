package com.sky7th.devtimeline.core.domain.tag.dto;

import com.sky7th.devtimeline.core.domain.tag.domain.Tag;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class TagResponseDto {

    private Long id;
    private String name;

    public static TagResponseDto of(Tag entity) {
        return new TagResponseDto(entity.getId(), entity.getName());
    }

    public static List<TagResponseDto> of(Set<Tag> entities) {
        return entities.stream().map(TagResponseDto::of).collect(Collectors.toList());
    }
}
