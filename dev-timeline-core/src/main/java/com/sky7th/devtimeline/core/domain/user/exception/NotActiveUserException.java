package com.sky7th.devtimeline.core.domain.user.exception;

public class NotActiveUserException extends RuntimeException {
    public NotActiveUserException() {
        super("비활성화된 유저입니다.");
    }
}
