package com.sky7th.devtimeline.api.presentation.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WebResponseDto <T> {
    public static final WebResponseDto<String> OK = new WebResponseDto<>(WebResponseStatus.OK);

    private WebResponseStatus status;
    private String message;
    private T data;

    @Builder
    public WebResponseDto(WebResponseStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public WebResponseDto(WebResponseStatus status) {
        this.status = status;
        this.message = status.getMessage();
    }

    public WebResponseDto(WebResponseStatus status, T data) {
        this.status = status;
        this.message = status.getMessage();
        this.data = data;
    }

}
