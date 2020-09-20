package com.sky7th.devtimeline.api.user.service;

import com.sky7th.devtimeline.core.domain.user.dto.SignUpRequestDto;
import com.sky7th.devtimeline.core.domain.user.dto.UserContext;
import com.sky7th.devtimeline.core.domain.user.dto.UserDetailResponseDto;
import com.sky7th.devtimeline.core.domain.user.service.UserInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserInternalService userInternalService;

  @PreAuthorize("@userInternalService.isMyself(#memberId, #userContext)")
  public UserDetailResponseDto findById(Long memberId, UserContext userContext) {
    return UserDetailResponseDto.of(userInternalService.findById(memberId));
  }

  public UserDetailResponseDto findByUserContext(UserContext userContext) {
    return UserDetailResponseDto.of(userInternalService.findById(userContext.getId()));
  }

  public UserDetailResponseDto register(SignUpRequestDto requestDto) {
    String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
    return UserDetailResponseDto.of(userInternalService.save(requestDto, encodedPassword));
  }
}
