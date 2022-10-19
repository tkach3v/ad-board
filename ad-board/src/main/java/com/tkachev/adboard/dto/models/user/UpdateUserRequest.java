package com.tkachev.adboard.dto.models.user;

import com.tkachev.adboard.entity.Role;
import com.tkachev.adboard.entity.UserStatus;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link com.tkachev.adboard.entity.User} entity
 */
@Data
public class UpdateUserRequest implements Serializable {
    @NotNull
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private UserStatus status;
    private Float rating;
    private Role role;
    @NotBlank
    @Email(message = "Please enter a valid email address")
    private String email;
    private String password;
}