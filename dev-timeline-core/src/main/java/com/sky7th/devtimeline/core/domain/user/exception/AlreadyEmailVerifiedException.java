package com.sky7th.devtimeline.core.domain.user.exception;

public class AlreadyEmailVerifiedException extends RuntimeException {
    public AlreadyEmailVerifiedException() {
        super("이미 이메일 인증된 회원입니다.");
    }
}
