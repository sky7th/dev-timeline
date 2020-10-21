package com.sky7th.devtimeline.post.post.exception;

public class NotFoundPostException extends RuntimeException {
    public NotFoundPostException() {
        super("존재하지 않는 글입니다.");
    }
}
