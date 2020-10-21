package com.sky7th.devtimeline.chat.inMemory.chatRoom.exception;

public class NotFoundChatRoomException extends RuntimeException {
    public NotFoundChatRoomException() {
        super("존재하지 않는 채팅방입니다.");
    }
}
