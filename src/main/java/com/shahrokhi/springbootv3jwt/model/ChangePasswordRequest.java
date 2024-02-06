package com.shahrokhi.springbootv3jwt.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;
    private String confirmationPassword;
}
