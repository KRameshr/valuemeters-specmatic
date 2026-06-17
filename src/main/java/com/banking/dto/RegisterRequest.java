//package com.banking.dto;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Size;
//
//public class RegisterRequest {
//
//    @NotBlank(message = "Name is required")
//    // Enforces that this must be treated as a String, not a coerced primitive
//    @JsonFormat(shape = JsonFormat.Shape.STRING)
//    private String name;
//
//    @NotBlank(message = "Email is required")
//    @JsonFormat(shape = JsonFormat.Shape.STRING)
//    private String email;
//
//    @NotBlank(message = "Password is required")
//    @Size(min = 6, message = "Password must be at least 6 characters")
//    @JsonFormat(shape = JsonFormat.Shape.STRING)
//    private String password;
//
//    // Getters and Setters
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//
//    public String getEmail() { return email; }
//    public void setEmail(String email) { this.email = email; }
//
//    public String getPassword() { return password; }
//    public void setPassword(String password) { this.password = password; }
//}

package com.banking.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    public RegisterRequest() {
        // default constructor (important for Jackson + Specmatic)
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null ? name.trim() : null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email != null ? email.trim().toLowerCase() : null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}