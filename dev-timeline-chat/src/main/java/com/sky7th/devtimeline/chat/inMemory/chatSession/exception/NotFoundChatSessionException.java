package com.sky7th.devtimeline.chat.inMemory.chatSession.exception;

public class NotFoundChatSessionException extends RuntimeException {
    public NotFoundChatSessionException() {
        super("채팅 세션 정보를 찾을 수 없습니다.");
    }
}
