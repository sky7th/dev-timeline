package com.sky7th.devtimeline.api.user.controller;

import com.sky7th.devtimeline.api.user.service.UserService;
import com.sky7th.devtimeline.core.domain.user.dto.UserContext;
import com.sky7th.devtimeline.core.domain.user.dto.UserDetailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @GetMapping("/user/me")
    public ResponseEntity<UserDetailResponseDto> getCurrentUser(UserContext userContext) {
        return ResponseEntity.ok(userService.findByUserContext(userContext));
    }

}
