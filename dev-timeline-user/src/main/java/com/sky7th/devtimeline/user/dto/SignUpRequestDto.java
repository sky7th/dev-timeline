package com.sky7th.devtimeline.user.dto;

import com.sky7th.devtimeline.user.AuthProvider;
import com.sky7th.devtimeline.user.UserRole;
import com.sky7th.devtimeline.user.domain.User;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public static User toSignedUpUser(SignUpRequestDto requestDto, String encodedPassword) {
        return User.builder()
            .name(requestDto.getName())
            .email(requestDto.getEmail())
            .emailVerified(false)
            .password(encodedPassword)
            .provider(AuthProvider.local)
            .userRole(UserRole.USER)
            .imageUrl(User.DEFAULT_USER_IMAGE_URL)
            .active(true)
            .build();
    }
}
