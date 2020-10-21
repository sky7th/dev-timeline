package com.sky7th.devtimeline.api.generic.mail.exception;

public class OverSendEmailLimitException extends RuntimeException {

  public OverSendEmailLimitException() {
    super("인증 이메일은 중복 3회까지만 발송이 가능합니다. 인증 이메일은 30분이 지나면 만료됩니다.");
  }
}
