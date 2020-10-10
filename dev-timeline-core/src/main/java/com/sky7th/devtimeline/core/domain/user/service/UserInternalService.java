package com.sky7th.devtimeline.core.domain.user.service;

import com.sky7th.devtimeline.core.domain.user.domain.User;
import com.sky7th.devtimeline.core.domain.user.domain.UserRepository;
import com.sky7th.devtimeline.core.domain.user.dto.SignUpRequestDto;
import com.sky7th.devtimeline.core.domain.user.dto.UserContext;
import com.sky7th.devtimeline.core.domain.user.exception.MismatchUserException;
import com.sky7th.devtimeline.core.domain.user.exception.NotFoundUserException;
import com.sky7th.devtimeline.core.domain.user.exception.UserAlreadyInUseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserInternalService {

  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public User findById(Long id) {
    return userRepository.findById(id).orElseThrow(NotFoundUserException::new);
  }

  @Transactional(readOnly = true)
  public User findByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(NotFoundUserException::new);
  }

  public User save(SignUpRequestDto requestDto, String encodedPassword) {
    if(userRepository.existsByEmail(requestDto.getEmail())) {
      throw new UserAlreadyInUseException();
    }
    return userRepository.save(SignUpRequestDto.toSignedUpUser(requestDto, encodedPassword));
  }

  public boolean isMyself(Long memberId, UserContext userContext) {
    if (!memberId.equals(userContext.getId())) {
      throw new MismatchUserException();
    }
    return true;
  }
}
