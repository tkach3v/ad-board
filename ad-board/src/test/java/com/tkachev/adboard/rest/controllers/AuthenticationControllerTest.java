package com.tkachev.adboard.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkachev.adboard.dto.models.auth.AuthenticationRequest;
import com.tkachev.adboard.dto.models.auth.AuthenticationResponse;
import com.tkachev.adboard.dto.models.user.CreateUserRequest;
import com.tkachev.adboard.dto.models.user.UpdateUserRequest;
import com.tkachev.adboard.dto.models.user.UserResponse;
import com.tkachev.adboard.security.JwtTokenProvider;
import com.tkachev.adboard.services.AuthenticationService;
import com.tkachev.adboard.services.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthenticationController.class)
class AuthenticationControllerTest {

    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String url = "/api/v1/auth";

    @Test
    void shouldAuthenticate() throws Exception {
        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setEmail("test@gmail.ru");
        authRequest.setEmail("test");
        when(authenticationService.authenticate(authRequest))
                .thenReturn(new AuthenticationResponse(authRequest.getEmail(), "token"));

        mockMvc.perform(post(url + "/login").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(authenticationService).authenticate(any(AuthenticationRequest.class));
    }

    @Test
    void shouldLogout() throws Exception {
        mockMvc.perform(post(url + "/logout"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}