package com.shahrokhi.springbootv3jwt.service;

import com.shahrokhi.springbootv3jwt.entity.User;
import com.shahrokhi.springbootv3jwt.model.ChangePasswordRequest;
import com.shahrokhi.springbootv3jwt.entity.UserRepository;
import com.shahrokhi.springbootv3jwt.model.UserAuthResponse;
import com.shahrokhi.springbootv3jwt.model.UserDetailsResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    public UserDetailsResponse whoAmI() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetailsResponse response = new UserDetailsResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setRoles(user.getRoles());
        response.setEnabled(user.isEnabled());
        return response;
    }

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(user);
    }
}
