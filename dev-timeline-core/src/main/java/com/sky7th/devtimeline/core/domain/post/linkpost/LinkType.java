package com.sky7th.devtimeline.core.domain.post.linkpost;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LinkType {

    TISTORY("tistory"),
    YOUTUBE("youtube"),
    BRUNCH("brunch");

    private final String name;

    public String getName() {
        return name;
    }

}
