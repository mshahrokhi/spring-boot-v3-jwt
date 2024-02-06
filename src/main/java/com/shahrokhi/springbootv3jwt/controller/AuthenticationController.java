package com.shahrokhi.springbootv3jwt.controller;

import com.shahrokhi.springbootv3jwt.model.UserAuthResponse;
import com.shahrokhi.springbootv3jwt.service.AuthenticationService;
import com.shahrokhi.springbootv3jwt.model.UserRegisterRequest;
import com.shahrokhi.springbootv3jwt.model.UserAuthRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<UserAuthResponse> register(@RequestBody UserRegisterRequest request) {
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<UserAuthResponse> authenticate(@RequestBody UserAuthRequest request) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<UserAuthResponse> refreshToken(HttpServletRequest request) throws IOException {
    return ResponseEntity.ok(service.refreshToken(request));
  }
}
