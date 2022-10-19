package com.tkachev.adboard.services;

import com.tkachev.adboard.config.TestConfig;
import com.tkachev.adboard.dto.mappers.UserMapper;
import com.tkachev.adboard.dto.models.user.CreateUserRequest;
import com.tkachev.adboard.dto.models.user.UpdateUserRequest;
import com.tkachev.adboard.dto.models.user.UserResponse;
import com.tkachev.adboard.entity.Role;
import com.tkachev.adboard.entity.User;
import com.tkachev.adboard.entity.UserStatus;
import com.tkachev.adboard.exception_handling.exceptions.NoSuchEntityException;
import com.tkachev.adboard.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void beforeEach() {
        reset(userRepository, passwordEncoder);
    }

    @Test
    void callEncodePasswordAndSaveUserWhenCreatingUser() {
        CreateUserRequest dto = new CreateUserRequest();
        dto.setPassword("password");

        userService.createUser(dto);

        verify(passwordEncoder).encode(dto.getPassword());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void setDefaultParametersIfTheyMissedWhenCreatingUser() {
        CreateUserRequest dto = new CreateUserRequest();

        UserResponse userResponse = userService.createUser(dto);

        assertEquals(UserStatus.ACTIVE, userResponse.getStatus());
        assertEquals(Role.USER, userResponse.getRole());
        assertEquals(0L, userResponse.getRating());
    }

    @Test
    void checkUserExistenceAndCallDeleteUserWhenDeletingUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));

        userService.deleteUser(anyLong());

        verify(userRepository).findById(anyLong());
        verify(userRepository).delete(any(User.class));
    }

    @Test
    void throwExceptionIfUserDoesNotExistWhenDeletingUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> userService.deleteUser(anyLong()));
    }

    @Test
    void checkUserExistenceAndSaveUserWhenUpdatingUser() {
        UpdateUserRequest dto = new UpdateUserRequest();
        dto.setId(0L);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));

        userService.updateUser(dto);

        verify(userRepository).findById(anyLong());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void callEncodePasswordIfPasswordChangedWhenUpdatingUser() {
        UpdateUserRequest dto = new UpdateUserRequest();
        dto.setId(0L);
        dto.setPassword("password");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));

        userService.updateUser(dto);

        verify(passwordEncoder).encode(dto.getPassword());
    }

    @Test
    void throwExceptionIfUserDoesNotExistWhenUpdatingUser() {
        UpdateUserRequest dto = new UpdateUserRequest();
        dto.setId(0L);
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> userService.updateUser(dto));
    }

    @Test
    void callFindAllWhenGettingUsers() {
        userService.getUsers();

        verify(userRepository).findAll();
    }

    @Test
    void checkUserExistenceWhenGettingUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));

        userService.getUserById(anyLong());

        verify(userRepository).findById(anyLong());
    }

    @Test
    void throwExceptionIfUserDoesNotExistWhenGettingUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> userService.getUserById(anyLong()));
    }
}