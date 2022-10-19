package com.tkachev.adboard.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkachev.adboard.dto.models.user.CreateUserRequest;
import com.tkachev.adboard.dto.models.user.UpdateUserRequest;
import com.tkachev.adboard.dto.models.user.UserResponse;
import com.tkachev.adboard.security.JwtTokenProvider;
import com.tkachev.adboard.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String url = "/api/v1/users";

    @Test
    void shouldCreateUser() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setFirstName("Ivan");
        createUserRequest.setLastName("Ivanov");
        createUserRequest.setEmail("ivanov@gmail.com");
        createUserRequest.setPassword("user");
        when(userService.createUser(createUserRequest)).thenReturn(new UserResponse());

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isCreated())
                .andDo(print());

        verify(userService).createUser(createUserRequest);
    }

    @Test
    void shouldUpdateUser() throws Exception {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setId(1L);
        updateUserRequest.setFirstName("Ivan");
        updateUserRequest.setLastName("Ivanov");
        updateUserRequest.setEmail("ivanov@gmail.com");
        updateUserRequest.setPassword("user");
        when(userService.updateUser(updateUserRequest)).thenReturn(new UserResponse());

        mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserRequest)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(userService).updateUser(updateUserRequest);
    }

    @Test
    void shouldGetUsers() throws Exception {
        when(userService.getUsers()).thenReturn(new ArrayList<>());

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andDo(print());

        verify(userService).getUsers();
    }

    @Test
    void shouldGetUserById() throws Exception {
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(new UserResponse());

        mockMvc.perform(get(url + "/" + userId))
                .andExpect(status().isOk())
                .andDo(print());

        verify(userService).getUserById(userId);
    }

    @Test
    void shouldDeleteUser() throws Exception {
        Long userId = 1L;

        mockMvc.perform(delete(url + "/" + userId))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(userService).deleteUser(userId);
    }
}