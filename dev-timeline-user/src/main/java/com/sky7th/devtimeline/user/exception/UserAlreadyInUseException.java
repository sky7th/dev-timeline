package com.sky7th.devtimeline.user.exception;

public class UserAlreadyInUseException extends RuntimeException {

  public UserAlreadyInUseException() {
    super("이미 가입되어 있는 이메일 입니다.");
  }
}
