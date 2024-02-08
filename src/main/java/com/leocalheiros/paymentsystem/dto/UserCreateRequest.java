package com.leocalheiros.paymentsystem.dto;

import com.leocalheiros.paymentsystem.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
        @NotNull(message = "The name cannot be empty!")
        @NotBlank(message = "Field cannot be null!") String name,
        @NotNull(message = "The email cannot be empty!")
        @NotBlank(message = "Field cannot be null!")
        @Email
        String email,
        @NotNull(message = "The password cannot be empty!")
        @NotBlank(message = "Field cannot be null!")
        @Size(min = 6, message = "Password needs at least 6 characters")
        String password){

    public User toModel(){
        return new User(name, email, password);
    }
}
