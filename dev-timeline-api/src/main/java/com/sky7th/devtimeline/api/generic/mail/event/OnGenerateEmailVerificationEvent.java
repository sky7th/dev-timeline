package com.sky7th.devtimeline.api.generic.mail.event;

import com.sky7th.devtimeline.core.domain.user.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@Setter
public class OnGenerateEmailVerificationEvent extends ApplicationEvent {

  private transient UriComponentsBuilder redirectUrl;
  private User user;

  public OnGenerateEmailVerificationEvent(User user, UriComponentsBuilder redirectUrl) {
    super(user);
    this.user = user;
    this.redirectUrl = redirectUrl;
  }
}
