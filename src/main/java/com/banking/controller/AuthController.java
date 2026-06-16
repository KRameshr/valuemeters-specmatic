package com.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid; // Ensure you import this
import java.util.Map;            // Import for Map to return JSON

import com.banking.dto.LoginRequest;
import com.banking.dto.RegisterRequest;
import com.banking.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Register and Login APIs")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Register new user")
    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @Valid @RequestBody RegisterRequest request) { // Added @Valid
        
        String response = authService.register(request);
        
        // Returning a Map automatically serializes to JSON
        return ResponseEntity.ok(Map.of("message", response));
    }

    @Operation(summary = "Login and get JWT token")
    @PostMapping("/login")
    public ResponseEntity<Object> login(
            @Valid @RequestBody LoginRequest request) { // Added @Valid
        
        String token = authService.login(request);
        // Returning a Map automatically serializes to JSON
        return ResponseEntity.ok(Map.of("token", token));
    }
}

