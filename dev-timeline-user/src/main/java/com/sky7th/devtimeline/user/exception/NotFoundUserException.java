package com.sky7th.devtimeline.user.exception;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException() {
        super("존재하지 않는 유저입니다.");
    }
}
