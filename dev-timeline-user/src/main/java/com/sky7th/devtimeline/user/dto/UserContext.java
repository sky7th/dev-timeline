package com.sky7th.devtimeline.user.dto;

import com.sky7th.devtimeline.user.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserContext {

  private Long id;
  private UserRole role;
  private String name;
  private String imageUrl;

  private UserContext() {
  }

  public UserContext(Long id, UserRole role, String name, String imageUrl) {
    this.id = id;
    this.role = role;
    this.name = name;
    this.imageUrl = imageUrl;
  }

  public boolean isAdmin() {
    return role == UserRole.ADMIN;
  }
}