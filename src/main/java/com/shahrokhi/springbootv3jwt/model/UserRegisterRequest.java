package com.shahrokhi.springbootv3jwt.model;

import com.shahrokhi.springbootv3jwt.entity.UserRole;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
  private String username;
  private String password;
  private Set<UserRole> roles = new HashSet<>();
}
