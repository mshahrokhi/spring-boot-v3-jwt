package com.shahrokhi.springbootv3jwt.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shahrokhi.springbootv3jwt.model.UserRegisterRequest;
import com.shahrokhi.springbootv3jwt.entity.Token;
import com.shahrokhi.springbootv3jwt.entity.TokenRepository;
import com.shahrokhi.springbootv3jwt.entity.User;
import com.shahrokhi.springbootv3jwt.entity.UserRepository;
import com.shahrokhi.springbootv3jwt.model.UserAuthRequest;
import com.shahrokhi.springbootv3jwt.model.UserAuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public UserAuthResponse register(UserRegisterRequest request) {
    User user = new User();
    user.setUsername(request.getUsername());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRoles(request.getRoles());
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(savedUser, jwtToken);
    return UserAuthResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
  }

  public UserAuthResponse authenticate(UserAuthRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()
        )
    );
    var user = repository.findByUsername(request.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return UserAuthResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    Token token = new Token();
    token.setUser(user);
    token.setToken(jwtToken);
    token.setExpired(false);
    token.setRevoked(false);
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public UserAuthResponse refreshToken(HttpServletRequest request) throws IOException {
    UserAuthResponse userAuthResponse = new UserAuthResponse();
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String username;
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return userAuthResponse;
    }
    refreshToken = authHeader.substring(7);
    username = jwtService.extractUsername(refreshToken);
    if (username != null) {
      var user = this.repository.findByUsername(username)
              .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        userAuthResponse.setAccessToken(accessToken);
        userAuthResponse.setRefreshToken(refreshToken);
      }
    }
    return userAuthResponse;
  }
}
