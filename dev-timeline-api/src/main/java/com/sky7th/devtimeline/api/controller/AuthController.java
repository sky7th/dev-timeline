package com.sky7th.devtimeline.api.controller;

import com.sky7th.devtimeline.core.domain.user.AuthProvider;
import com.sky7th.devtimeline.core.domain.user.User;
import com.sky7th.devtimeline.core.domain.user.UserRepository;
import com.sky7th.devtimeline.core.domain.user.UserRole;
import com.sky7th.devtimeline.api.exception.BadRequestException;
import com.sky7th.devtimeline.api.presentation.api.dto.AuthResponseDto;
import com.sky7th.devtimeline.api.presentation.api.dto.LoginRequestDto;
import com.sky7th.devtimeline.api.presentation.api.dto.SignUpRequestDto;
import com.sky7th.devtimeline.api.presentation.api.dto.WebResponseDto;
import com.sky7th.devtimeline.api.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.sky7th.devtimeline.api.presentation.api.dto.WebResponseStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static String DEFAULT_USER_IMAGE_URL = "https://image.flaticon.com/icons/svg/1987/1987936.svg";

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequestDto signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("존재하는 이메일입니다.");
        }

        User user = User.builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .emailVerified(false)
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .provider(AuthProvider.local)
                .userRole(UserRole.USER)
                .imageUrl(DEFAULT_USER_IMAGE_URL)
                .build();

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(WebResponseDto.builder().status(OK).data("유저 회원가입 성공").build());

    }

}
