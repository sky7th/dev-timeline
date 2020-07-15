package com.sky7th.devtimeline.web.service.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortOrderType {

    DESC("desc"),
    ASC("asc"),
    LIKE("like");

    private final String name;

    public String getName() {
        return name;
    }

}