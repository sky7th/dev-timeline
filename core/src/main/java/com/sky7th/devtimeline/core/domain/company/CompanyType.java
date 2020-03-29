package com.sky7th.devtimeline.core.domain.company;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CompanyType {

    NAVER("네이버"),
    KAKAO("카카오");

    private final String name;

    public String getName() {
        return name;
    }

}
