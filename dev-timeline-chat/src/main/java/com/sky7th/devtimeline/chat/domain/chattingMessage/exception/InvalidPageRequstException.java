package com.sky7th.devtimeline.chat.domain.chattingMessage.exception;

public class InvalidPageRequstException extends RuntimeException {
    public InvalidPageRequstException() {
        super("페이징 크기는 수정할 수 없습니다.");
    }
}
