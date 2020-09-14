package com.sky7th.devtimeline.api.controller;

import com.sky7th.devtimeline.core.domain.user.User;
import com.sky7th.devtimeline.core.domain.user.UserRepository;
import com.sky7th.devtimeline.api.exception.ResourceNotFoundException;
import com.sky7th.devtimeline.api.security.CurrentUser;
import com.sky7th.devtimeline.api.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {

        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

}
