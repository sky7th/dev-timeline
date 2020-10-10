package com.sky7th.devtimeline.core.domain.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sky7th.devtimeline.core.domain.common.BaseTimeEntity;
import com.sky7th.devtimeline.core.domain.user.AuthProvider;
import com.sky7th.devtimeline.core.domain.user.UserRole;
import com.sky7th.devtimeline.core.domain.user.exception.AlreadyEmailVerifiedException;
import com.sun.istack.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User extends BaseTimeEntity {

    public static String DEFAULT_USER_IMAGE_URL = "https://image.flaticon.com/icons/svg/1987/1987936.svg";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Column(nullable = false)
    private String name;

//    @Email
    @Column(nullable = false)
    private String email;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @JsonIgnore
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @Column(nullable = false)
    private Boolean active = true;

    public User(User user) {
        this.id = user.getId();
        this.userRole = user.getUserRole();
        this.name = user.getName();
        this.email = user.getEmail();
        this.imageUrl = user.getImageUrl();
        this.emailVerified = user.getEmailVerified();
        this.password = user.getPassword();
        this.provider = user.getProvider();
        this.providerId = user.getProviderId();
        this.active = user.getActive();
    }

    public User(Long id) {
        this.id = id;
    }

    public void emailVerify() {
        if (this.emailVerified) {
            throw new AlreadyEmailVerifiedException();
        }
        this.emailVerified = true;
    }
}
