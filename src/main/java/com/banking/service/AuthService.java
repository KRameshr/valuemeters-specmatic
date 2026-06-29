package com.banking.service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banking.dto.LoginRequest;
import com.banking.dto.RegisterRequest;
import com.banking.model.Account;
import com.banking.model.User;
import com.banking.repo.AccountRepository;
import com.banking.repo.UserRepository;
import com.banking.security.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // REGISTER
    public String register(RegisterRequest request) {

        if (request == null
                || request.getName() == null
                || request.getEmail() == null
                || request.getPassword() == null) {
            throw new IllegalArgumentException("All fields are required");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.USER);

        userRepository.save(user);

        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(BigDecimal.ZERO);
        account.setUser(user);

        accountRepository.save(account);

        return "User registered successfully! Account Number: "
                + account.getAccountNumber();
    }

    // LOGIN
    public String login(LoginRequest request) {

        if (request == null
                || request.getEmail() == null
                || request.getPassword() == null) {
            throw new IllegalArgumentException("Email and password required");
        }

        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (!userOpt.isPresent()) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        User user = userOpt.get();

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword());

        if (!matches) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getId());
    }

    private String generateAccountNumber() {
        return "ACC" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }
}