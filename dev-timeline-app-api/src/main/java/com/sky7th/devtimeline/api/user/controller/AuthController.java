package com.sky7th.devtimeline.api.user.controller;

import com.sky7th.devtimeline.api.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/signup/confirm")
    public String registerConfirm(@RequestParam(value = "key", required = false) String key, Model model) {
        model.addAttribute("message", authService.emailVerify(key));
        return "email-confirm";
    }
}
