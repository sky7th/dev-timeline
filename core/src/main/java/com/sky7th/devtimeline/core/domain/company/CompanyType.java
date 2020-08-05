package com.sky7th.devtimeline.core.domain.company;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CompanyType {

    NAVER("네이버"),
    KAKAO("카카오"),
    WOOWABROS("우아한형제들"),
    LINE("라인"),
    ZUM("줌");

    private final String name;

    public String getName() {
        return name;
    }

}
