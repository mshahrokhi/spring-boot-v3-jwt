package com.shahrokhi.springbootv3jwt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @GetMapping
    public String get() {
        return "AdminController :: GET";
    }

    @PostMapping
    public String post() {
        return "AdminController :: POST";
    }

    @PutMapping
    public String put() {
        return "AdminController :: PUT";
    }

    @DeleteMapping
    public String delete() {
        return "AdminController :: DELETE";
    }
}
