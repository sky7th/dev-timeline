package com.sky7th.devtimeline.core.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sky7th.devtimeline.core.domain.common.BaseTimeEntity;
import com.sun.istack.NotNull;
import lombok.*;
//import javax.validation.constraints.Email;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User extends BaseTimeEntity {

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

    @Builder
    public User(Long id, UserRole userRole, String name, String email, String imageUrl, Boolean emailVerified, String password, AuthProvider provider, String providerId) {
        this.id = id;
        this.userRole = userRole;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.emailVerified = emailVerified;
        this.password = password;
        this.provider = provider;
        this.providerId = providerId;
    }

    public String getRole() {
        return this.userRole.getRole();
    }
}
