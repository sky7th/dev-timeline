package com.sky7th.devtimeline.web.presentation.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDto {

    private String accessToken;
    private String tokenType = "Bearer";

    public AuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

}