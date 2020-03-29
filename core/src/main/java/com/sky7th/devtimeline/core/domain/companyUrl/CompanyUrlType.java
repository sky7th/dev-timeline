package com.sky7th.devtimeline.core.domain.companyUrl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CompanyUrlType {

    RECRUIT("RECRUIT"),
    TECH("TECH");

    private final String name;

    public String getName() {
        return name;
    }

}
