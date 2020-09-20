package com.sky7th.devtimeline.api.user.controller;

import com.sky7th.devtimeline.api.user.CustomUserDetails;
import com.sky7th.devtimeline.api.user.service.AuthService;
import com.sky7th.devtimeline.api.user.service.UserService;
import com.sky7th.devtimeline.core.domain.user.dto.AuthResponseDto;
import com.sky7th.devtimeline.core.domain.user.dto.LoginRequestDto;
import com.sky7th.devtimeline.core.domain.user.dto.SignUpRequestDto;
import com.sky7th.devtimeline.core.domain.user.dto.UserDetailResponseDto;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        CustomUserDetails customUserDetails = authService.authenticateUser(
            new UsernamePasswordAuthenticationToken(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword()
        ));
        return ResponseEntity.ok(authService.createJwtToken(customUserDetails));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDetailResponseDto> register(@Valid @RequestBody SignUpRequestDto requestDto) {
        return ResponseEntity.ok(userService.register(requestDto));
    }

    @GetMapping("/health")
    public ResponseEntity<Void> checkHealth() {
        return ResponseEntity.ok().build();
    }
}
