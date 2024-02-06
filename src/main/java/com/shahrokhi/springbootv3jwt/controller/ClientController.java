package com.shahrokhi.springbootv3jwt.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @GetMapping
    public String get() {
        return "ClientController :: GET";
    }

    @PostMapping
    public String post() {
        return "ClientController :: POST";
    }

    @PutMapping
    public String put() {
        return "ClientController :: PUT";
    }

    @DeleteMapping
    public String delete() {
        return "ClientController :: DELETE";
    }
}
