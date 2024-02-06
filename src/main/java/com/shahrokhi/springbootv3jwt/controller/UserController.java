package com.shahrokhi.springbootv3jwt.controller;

import com.shahrokhi.springbootv3jwt.entity.User;
import com.shahrokhi.springbootv3jwt.model.ChangePasswordRequest;
import com.shahrokhi.springbootv3jwt.model.UserDetailsResponse;
import com.shahrokhi.springbootv3jwt.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/who-am-i")
    public ResponseEntity<UserDetailsResponse> whoAmI() {
        return ResponseEntity.ok(service.whoAmI());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, Principal connectedUser) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
}
