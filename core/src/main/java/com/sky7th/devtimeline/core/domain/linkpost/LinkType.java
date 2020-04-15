package com.sky7th.devtimeline.core.domain.linkpost;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LinkType {

    BLOG("블로그"),
    VIDEO("영상"),
    EXTRA("나머지");

    private final String name;

    public String getName() {
        return name;
    }

}
