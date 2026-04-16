package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDto {
    @NotBlank(message = "Name must be provided")
    @Size(max = 50)
    private String name;

    @NotBlank(message = "Email must be provided")
    @Email(message = "Email must be valid")
    private String email;

    public UserDto() {}

    public UserDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /* Getters & Setters */
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}