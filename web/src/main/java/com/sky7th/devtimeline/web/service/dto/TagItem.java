package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.tag.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TagItem {

    private String name;

    public TagItem(Tag tag) {
        this.name = tag.getName();
    }

    public Tag toTag() {
        return Tag.builder()
                .name(this.name)
                .build();
    }

}
