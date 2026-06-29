package com.banking.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
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
    public ResponseEntity<Object> register(
            @Valid @RequestBody RegisterRequest request) {

        
        System.out.println("REGISTER CONTROLLER HIT");
        System.out.println(request.getEmail());        

        String response = authService.register(request);

        return ResponseEntity.ok(
                Map.of("message", response)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(
        @RequestBody LoginRequest request) {

        System.out.println("AUTH CONTROLLER LOGIN HIT");
        System.out.println("Login email: " + request.getEmail());
        String token = authService.login(request);

        return ResponseEntity.ok(
                Map.of("token", token)
        );
    }
}