package com.sky7th.devtimeline.chat.service.exception;

public class AlreadyExistChatRoomException extends RuntimeException {
    public AlreadyExistChatRoomException() {
        super("이미 존재하는 채팅방 이름입니다.");
    }
}
