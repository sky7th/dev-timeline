package com.sky7th.devtimeline.post.post.exception;

public class MismatchAuthorException extends RuntimeException {
    public MismatchAuthorException() {
        super("글을 쓴 유저가 아닙니다.");
    }
}
