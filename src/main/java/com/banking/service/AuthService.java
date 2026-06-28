

package com.banking.service;

import java.math.BigDecimal;
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
    // public String register(RegisterRequest request) {
    
    public String register(RegisterRequest request) {

    System.out.println("REGISTER SERVICE HIT");

    if (request == null
            || request.getName() == null
            || request.getEmail() == null
            || request.getPassword() == null) {
        throw new IllegalArgumentException("All fields are required");
    }

    System.out.println("CHECKING EMAIL = " + request.getEmail());

    boolean exists = userRepository.existsByEmail(request.getEmail());

    System.out.println("EMAIL EXISTS = " + exists);

    if (exists) {
        throw new IllegalArgumentException("Email already exists");
    }

    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole(User.Role.USER);

    System.out.println("BEFORE USER SAVE");

    try {
        userRepository.save(user);
        System.out.println("USER SAVED");
    } catch (Exception e) {
        System.out.println("USER SAVE FAILED");
        e.printStackTrace();
        throw e;
    }

    Account account = new Account();
    account.setAccountNumber(generateAccountNumber());
    account.setBalance(BigDecimal.ZERO);
    account.setUser(user);

    System.out.println("BEFORE ACCOUNT SAVE");

    try {
        accountRepository.save(account);
        System.out.println("ACCOUNT SAVED");
    } catch (Exception e) {
        System.out.println("ACCOUNT SAVE FAILED");
        e.printStackTrace();
        throw e;
    }

    return "User registered successfully! Account Number: "
            + account.getAccountNumber();
}
   

    

    //     if (request == null
    //             || request.getName() == null
    //             || request.getEmail() == null
    //             || request.getPassword() == null) {
    //         throw new IllegalArgumentException("All fields are required");
    //     }

    //     if (userRepository.existsByEmail(request.getEmail())) {
    //         throw new IllegalArgumentException("Email already exists");
    //     }

    //     User user = new User();
    //     user.setName(request.getName());
    //     user.setEmail(request.getEmail());
    //     user.setPassword(passwordEncoder.encode(request.getPassword()));
    //     user.setRole(User.Role.USER);

    //     userRepository.save(user);

    //     Account account = new Account();
    //     account.setAccountNumber(generateAccountNumber());
    //     account.setBalance(BigDecimal.ZERO);
    //     account.setUser(user);

    //     accountRepository.save(account);

    //     return "User registered successfully! Account Number: " + account.getAccountNumber();
    // }

    // // LOGIN (IMPORTANT FIX)
    // public String login(LoginRequest request) {

    // if (request == null
    //         || request.getEmail() == null
    //         || request.getPassword() == null) {
    //     throw new IllegalArgumentException("Email and password required");
    // }

    // java.util.Optional<User> userOpt =
    //         userRepository.findByEmail(request.getEmail());


    // if (!userOpt.isPresent()) {
    //     throw new IllegalArgumentException("Invalid credentials");
    // }

    // User user = userOpt.get();


    // boolean matches = passwordEncoder.matches(
    //         request.getPassword(),
    //         user.getPassword());

    // if (!matches) {
    //     throw new IllegalArgumentException("Invalid credentials");
    // }

    // return jwtUtil.generateToken(user.getEmail(), user.getId());
    // }
    
    public String login(LoginRequest request) {

        System.out.println("LOGIN METHOD HIT");

        if (request == null
                || request.getEmail() == null
                || request.getPassword() == null) {
            throw new IllegalArgumentException("Email and password required");
        }

        System.out.println("========== LOGIN DEBUG ==========");
        System.out.println("Login email: " + request.getEmail());
        System.out.println("USER COUNT = " + userRepository.count());

        System.out.println("All users in DB:");
        userRepository.findAll().forEach(u ->
                System.out.println(
                        "ID=" + u.getId()
                        + ", Email=" + u.getEmail()
                        + ", Password=" + u.getPassword()
                )
        );

        java.util.Optional<User> userOpt =
                userRepository.findByEmail(request.getEmail());

        System.out.println("FOUND = " + userOpt.isPresent());

        if (!userOpt.isPresent()) {
            System.out.println("Reason: User not found");
            throw new IllegalArgumentException("Invalid credentials");
        }

        User user = userOpt.get();

        System.out.println("DB EMAIL = " + user.getEmail());
        System.out.println("DB PASSWORD = " + user.getPassword());

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword());

        System.out.println("PASSWORD MATCH = " + matches);

        if (!matches) {
            System.out.println("Reason: Password mismatch");
            throw new IllegalArgumentException("Invalid credentials");
        }

        System.out.println("Login SUCCESS");
        System.out.println("===============================");

        return jwtUtil.generateToken(user.getEmail(), user.getId());
    }
    private String generateAccountNumber() {
        return "ACC" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }
}