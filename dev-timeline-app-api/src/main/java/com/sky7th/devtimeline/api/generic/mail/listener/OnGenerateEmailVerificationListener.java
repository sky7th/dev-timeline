package com.sky7th.devtimeline.api.generic.mail.listener;

import com.sky7th.devtimeline.api.generic.mail.domain.MailService;
import com.sky7th.devtimeline.api.generic.mail.domain.token.EmailVerificationToken;
import com.sky7th.devtimeline.api.generic.mail.domain.token.EmailVerificationTokenService;
import com.sky7th.devtimeline.api.generic.mail.event.OnGenerateEmailVerificationEvent;
import com.sky7th.devtimeline.api.generic.mail.listener.Exception.MailSendException;
import com.sky7th.devtimeline.user.domain.User;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import javax.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class OnGenerateEmailVerificationListener {

  private static final Logger logger = LoggerFactory.getLogger(
      OnGenerateEmailVerificationListener.class);

  private final MailService mailService;
  private final EmailVerificationTokenService emailVerificationTokenService;

  @Async
  @EventListener
  public void onApplicationEvent(OnGenerateEmailVerificationEvent onGenerateEmailVerificationEvent) {
    sendEmailVerification(onGenerateEmailVerificationEvent);
  }

  @Transactional
  public void sendEmailVerification(OnGenerateEmailVerificationEvent event) {
    User user = event.getUser();
    String toEmail = user.getEmail();
    String key = UUID.randomUUID().toString();
    Instant emailVerificationTokenExpiryDate = emailVerificationTokenService.getEmailVerificationTokenExpiryDate();
    emailVerificationTokenService.save(new EmailVerificationToken(key, user.getId(), emailVerificationTokenExpiryDate));
    String emailConfirmationUrl = event.getRedirectUrl().queryParam("key", key).toUriString();

    try {
      mailService.sendEmailVerification(emailConfirmationUrl, toEmail);

    } catch (IOException | MessagingException | freemarker.template.TemplateException e) {
      logger.error(String.valueOf(e));
      throw new MailSendException(toEmail, "Email Verification");
    }
  }
}
