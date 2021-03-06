package com.sky7th.devtimeline.crawlpost.company.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CompanyType {

    ALL("ALL"),
    NAVER("네이버"),
    KAKAO("카카오"),
    WOOWABROS("우아한형제들"),
    LINE("라인"),
    ZUM("줌"),
    NHN("NHN");

    private final String name;

    public String getName() {
        return name;
    }

}
