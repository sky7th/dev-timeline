package com.sky7th.devtimeline.api.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class UserLoginException extends RuntimeException {

  public UserLoginException() {
    super("해당 정보로 로그인 할 수 없습니다.");
  }
}