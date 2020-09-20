package com.sky7th.devtimeline.core.domain.user.dto;

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
