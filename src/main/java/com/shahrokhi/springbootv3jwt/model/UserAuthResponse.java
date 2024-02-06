package com.shahrokhi.springbootv3jwt.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthResponse {
  private String accessToken;
  private String refreshToken;
}
