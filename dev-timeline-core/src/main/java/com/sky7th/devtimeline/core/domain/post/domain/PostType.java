package com.sky7th.devtimeline.core.domain.post.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostType {

    RECRUIT_POST("채용 게시물"),
    TECH_POST("기술 블로그 게시물"),
    LINK_POST("링크 게시물");

    private final String name;

    public String getName() {
        return name;
    }

}
