package com.shahrokhi.springbootv3jwt.entity;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
  ADMIN,
  CLIENT;

  public String getAuthority() {
    return name();
  }
}
