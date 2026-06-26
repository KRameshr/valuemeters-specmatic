package com.banking.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.banking.config.StrictStringDeserializer;


public class RegisterRequest {

    @NotBlank(message = "Name is required")
    @JsonDeserialize(using = StrictStringDeserializer.class)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password is required")
    @JsonDeserialize(using = StrictStringDeserializer.class)
    @Size(min = 5, message = "Password must be at least 5 characters")
    private String password;

    public RegisterRequest() {
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