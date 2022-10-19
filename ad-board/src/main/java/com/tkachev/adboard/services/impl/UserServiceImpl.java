package com.tkachev.adboard.services.impl;

import com.tkachev.adboard.dto.models.user.CreateUserRequest;
import com.tkachev.adboard.dto.models.user.UpdateUserRequest;
import com.tkachev.adboard.dto.models.user.UserResponse;
import com.tkachev.adboard.entity.Role;
import com.tkachev.adboard.entity.User;
import com.tkachev.adboard.dto.mappers.UserMapper;
import com.tkachev.adboard.entity.UserStatus;
import com.tkachev.adboard.repositories.UserRepository;
import com.tkachev.adboard.services.AbstractService;
import com.tkachev.adboard.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends AbstractService implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(CreateUserRequest dto) {
        User user = userMapper.toUser(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Objects.requireNonNullElse(user.getStatus(), UserStatus.ACTIVE));
        user.setRole(Objects.requireNonNullElse(user.getRole(), Role.USER));
        user.setRating(Objects.requireNonNullElse(user.getRating(), 0f));
        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        user = isNotNull(user, "User", id);
        userRepository.delete(user);
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest dto) {
        User user = userRepository.findById(dto.getId()).orElse(null);
        user = isNotNull(user, "User", dto.getId());
        userMapper.updateUser(dto, user);
        if (dto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getUsers() {
        return userRepository.findAll().
                stream().
                map(userMapper::toUserResponse).
                toList();
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        user = isNotNull(user, "User", id);

        return userMapper.toUserResponse(user);
    }
}
