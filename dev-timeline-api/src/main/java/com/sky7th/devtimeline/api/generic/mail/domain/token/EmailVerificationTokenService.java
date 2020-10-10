package com.sky7th.devtimeline.api.generic.mail.domain.token;

import com.sky7th.devtimeline.api.security.exception.TokenRefreshException;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailVerificationTokenService {

  @Value("${spring.mail.emailVerificationTokenExpiration}")
  private long emailVerificationTokenExpirationMs;

  private final EmailVerificationTokenRepository emailVerificationTokenRepository;

  public EmailVerificationToken findById(String id) {
    return emailVerificationTokenRepository.findById(id)
        .orElseThrow(() -> new TokenRefreshException("이메일 인증 시간이 만료되었습니다. 이메일 인증 요청을 다시 해주세요."));
  }

  public EmailVerificationToken save(EmailVerificationToken refreshToken) {
    return emailVerificationTokenRepository.save(refreshToken);
  }

  public Instant getEmailVerificationTokenExpiryDate() {
    return Instant.now().plusMillis(emailVerificationTokenExpirationMs);
  }
}
