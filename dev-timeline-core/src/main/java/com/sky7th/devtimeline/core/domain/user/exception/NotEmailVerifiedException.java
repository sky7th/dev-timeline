package com.sky7th.devtimeline.core.domain.user.exception;

public class NotEmailVerifiedException extends RuntimeException {
    public NotEmailVerifiedException() {
        super("이메일 인증이 완료되지 않았습니다.");
    }
}
