package com.tkachev.adboard.dto.models.user;

import com.tkachev.adboard.entity.Role;
import com.tkachev.adboard.entity.UserStatus;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.tkachev.adboard.entity.User} entity
 */
@Data
public class UserResponse implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private UserStatus status;
    private Float rating;
    private Role role;
    private String email;
}