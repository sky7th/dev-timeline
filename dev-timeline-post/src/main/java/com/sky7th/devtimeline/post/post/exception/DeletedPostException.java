package com.sky7th.devtimeline.post.post.exception;

public class DeletedPostException extends RuntimeException {
    public DeletedPostException() {
        super("삭제된 글입니다.");
    }
}
