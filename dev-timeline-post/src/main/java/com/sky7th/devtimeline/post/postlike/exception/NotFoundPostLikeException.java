package com.sky7th.devtimeline.post.postlike.exception;

public class NotFoundPostLikeException extends RuntimeException {
    public NotFoundPostLikeException() {
        super("좋아요를 한 글이 아닙니다.");
    }
}
