package com.tkachev.adboard.services;

import com.tkachev.adboard.dto.models.user.CreateUserRequest;
import com.tkachev.adboard.dto.models.user.UpdateUserRequest;
import com.tkachev.adboard.dto.models.user.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(CreateUserRequest dto);

    void deleteUser(Long id);

    UserResponse updateUser(UpdateUserRequest dto);

    List<UserResponse> getUsers();

    UserResponse getUserById(Long id);
}
