package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.tag.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TagDto {

    private Long id;
    private String name;

    public TagDto(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }

    public Tag toTag() {
        return Tag.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }

}
