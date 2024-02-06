package com.shahrokhi.springbootv3jwt.model;

import com.shahrokhi.springbootv3jwt.entity.UserRole;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsResponse {
  private Integer id;
  private String username;
  private Set<UserRole> roles;
  private boolean enabled;
}
