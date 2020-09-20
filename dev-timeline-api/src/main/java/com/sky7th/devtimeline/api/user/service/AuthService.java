package com.sky7th.devtimeline.api.user.service;

import com.sky7th.devtimeline.api.security.TokenProvider;
import com.sky7th.devtimeline.api.security.exception.NotLoginException;
import com.sky7th.devtimeline.api.security.exception.UserLoginException;
import com.sky7th.devtimeline.api.user.CustomUserDetails;
import com.sky7th.devtimeline.core.domain.user.dto.AuthResponseDto;
import com.sky7th.devtimeline.core.domain.user.dto.UserContext;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final TokenProvider tokenProvider;

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

  public void isLogin(UserContext userContext) {
    if (userContext.getId() == null) {
      throw new NotLoginException();
    }
  }
}
