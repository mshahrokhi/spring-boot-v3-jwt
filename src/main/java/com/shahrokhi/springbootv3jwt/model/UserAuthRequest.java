package com.shahrokhi.springbootv3jwt.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthRequest {
  private String username;
  String password;
}
