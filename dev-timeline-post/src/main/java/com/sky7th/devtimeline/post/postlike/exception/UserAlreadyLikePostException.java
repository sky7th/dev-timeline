package com.sky7th.devtimeline.post.postlike.exception;

public class UserAlreadyLikePostException extends RuntimeException {
    public UserAlreadyLikePostException() {
        super("이미 좋아요를 한 글입니다.");
    }
}
