package com.tkachev.adboard.dto.models.auth;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class AuthenticationRequest {

    @NotBlank
    @Email(message = "Please enter a valid email address")
    private String email;
    @NotBlank
    private String password;
}
