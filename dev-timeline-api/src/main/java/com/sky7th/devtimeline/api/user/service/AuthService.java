package com.sky7th.devtimeline.api.user.service;

import com.sky7th.devtimeline.api.generic.mail.domain.token.EmailVerificationToken;
import com.sky7th.devtimeline.api.generic.mail.domain.token.EmailVerificationTokenService;
import com.sky7th.devtimeline.api.generic.mail.event.OnGenerateEmailVerificationEvent;
import com.sky7th.devtimeline.api.generic.mail.exception.OverSendEmailLimitException;
import com.sky7th.devtimeline.api.security.TokenProvider;
import com.sky7th.devtimeline.api.security.exception.UserLoginException;
import com.sky7th.devtimeline.api.user.CustomUserDetails;
import com.sky7th.devtimeline.core.domain.user.domain.User;
import com.sky7th.devtimeline.core.domain.user.dto.AuthResponseDto;
import com.sky7th.devtimeline.core.domain.user.dto.LoginRequestDto;
import com.sky7th.devtimeline.core.domain.user.dto.SignUpRequestDto;
import com.sky7th.devtimeline.core.domain.user.dto.UserDetailResponseDto;
import com.sky7th.devtimeline.core.domain.user.exception.AlreadyEmailVerifiedException;
import com.sky7th.devtimeline.core.domain.user.service.UserInternalService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Service
public class AuthService {

  private static final String USER_REGISTER_CONFIRM_URL = "/auth/signup/confirm";
  private static final String VERIFICATION_SUCCESS_MESSAGE = "인증을 완료했습니다.";

  private final AuthenticationManager authenticationManager;
  private final TokenProvider tokenProvider;
  private final PasswordEncoder passwordEncoder;
  private final UserInternalService userInternalService;
  private final EmailVerificationTokenService emailVerificationTokenService;
  private final ApplicationEventPublisher applicationEventPublisher;

  public CustomUserDetails authenticateUser(
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
    Authentication authentication = Optional
        .ofNullable(authenticationManager.authenticate(usernamePasswordAuthenticationToken))
        .orElseThrow(UserLoginException::new);

    SecurityContextHolder.getContext().setAuthentication(authentication);

    return (CustomUserDetails) authentication.getPrincipal();
  }

  public AuthResponseDto createJwtToken(CustomUserDetails customUserDetails) {
    return new AuthResponseDto(tokenProvider.createToken(customUserDetails));
  }

  @Transactional
  public UserDetailResponseDto register(SignUpRequestDto requestDto) {
    String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
    User user = userInternalService.save(requestDto, encodedPassword);
    sendVerificationEmail(user);

    return UserDetailResponseDto.of(user);
  }

  private void sendVerificationEmail(User user) {
    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path(USER_REGISTER_CONFIRM_URL);
    OnGenerateEmailVerificationEvent onGenerateEmailVerificationEvent = new OnGenerateEmailVerificationEvent(user, urlBuilder);
    applicationEventPublisher.publishEvent(onGenerateEmailVerificationEvent);
  }

  @Transactional
  public String emailVerify(String key) {
    try {
      EmailVerificationToken token = emailVerificationTokenService.findById(key);
      token.verifyExpiration();
      User user = userInternalService.findById(token.getUserId());
      user.emailVerify();

      return VERIFICATION_SUCCESS_MESSAGE;

    } catch (Exception e) {
      return e.getMessage();
    }
  }

  public void resendVerificationEmail(LoginRequestDto loginRequestDto) {
    User user = userInternalService.findByEmail(loginRequestDto.getEmail());
    validateUser(user, loginRequestDto.getPassword());

    if (emailVerificationTokenService.isOverEmailRequestLimit(user.getId())) {
      throw new OverSendEmailLimitException();
    }

    sendVerificationEmail(user);
  }

  public void validateUser(User user, String enteredPassword) {
    if (!passwordEncoder.matches(enteredPassword, user.getPassword())) {
      throw new BadCredentialsException(user.getEmail());
    }

    if (!user.getActive()) {
      throw new LockedException(user.getEmail());
    }

    if (user.getEmailVerified()) {
      throw new AlreadyEmailVerifiedException();
    }
  }
}
