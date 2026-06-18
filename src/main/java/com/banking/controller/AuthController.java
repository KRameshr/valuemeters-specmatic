package com.banking.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banking.dto.LoginRequest;
import com.banking.dto.RegisterRequest;
import com.banking.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request) {

        if (request.getName() == null ||
            request.getEmail() == null ||
            request.getPassword() == null) {

            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Missing fields"));
        }

        String response = authService.register(request);

        return ResponseEntity.ok(Map.of("message", response));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {

        if (request.getEmail() == null ||
            request.getPassword() == null) {

            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Missing fields"));
        }

        String token = authService.login(request);

        return ResponseEntity.ok(Map.of("token", token));
    }
}