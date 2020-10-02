package com.sky7th.devtimeline.chat.service.exception;

public class NotFoundChatUserException extends RuntimeException {
    public NotFoundChatUserException() {
        super("채팅 사용자 중에서 찾을 수 없습니다.");
    }
}
