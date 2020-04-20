package com.sky7th.devtimeline.web.service.dto;

import com.sky7th.devtimeline.core.domain.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.sky7th.devtimeline.core.domain.utils.LocalDateTimeUtils.toStringDate;

@RequiredArgsConstructor
@Getter
public class UserItem {

    private Long id;
    private String name;
    private String email;
    private String imageUrl;
    private String createdDate;

    public UserItem(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.imageUrl = user.getImageUrl();
        this.createdDate = toStringDate(user.getCreatedDate(), "yyyy-MM-dd");
    }

    public User toUser() {
        return User.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .imageUrl(this.imageUrl)
                .build();
    }

    public void changeImageUrl(String imageUrl) {

    }

}
