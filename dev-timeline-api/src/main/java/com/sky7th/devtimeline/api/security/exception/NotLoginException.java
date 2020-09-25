package com.sky7th.devtimeline.api.security.exception;

public class NotLoginException extends RuntimeException {

  public NotLoginException() {
    super("로그인이 필요합니다.");
  }
}
