package com.sky7th.devtimeline.chat.service.exception;

public class AlreadyDeletedChatRoomException extends RuntimeException {
    public AlreadyDeletedChatRoomException() {
        super("이미 삭제된 채팅방입니다.");
    }
}
