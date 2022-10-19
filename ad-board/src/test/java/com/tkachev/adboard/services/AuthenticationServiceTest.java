package com.tkachev.adboard.services;

import com.tkachev.adboard.config.TestConfig;
import com.tkachev.adboard.dto.models.auth.AuthenticationRequest;
import com.tkachev.adboard.entity.Role;
import com.tkachev.adboard.entity.User;
import com.tkachev.adboard.repositories.UserRepository;
import com.tkachev.adboard.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest
class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    private void beforeEach() {
        reset(userRepository, jwtTokenProvider, authenticationManager);
    }

    @Test
    void authenticateTest() {
        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setEmail("test@gmail.ru");
        authRequest.setPassword("test");
        User user = new User();
        user.setRole(Role.ADMIN);
        UsernamePasswordAuthenticationToken authenticationToken = mock(UsernamePasswordAuthenticationToken.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authenticationToken);
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtTokenProvider.createToken(authRequest.getEmail(), user.getRole().name())).thenReturn(anyString());

        authenticationService.authenticate(authRequest);

        verify(authenticationManager).authenticate(any(Authentication.class));
        verify(userRepository).findUserByEmail(authRequest.getEmail());
        verify(jwtTokenProvider).createToken(authRequest.getEmail(), Role.ADMIN.name());
    }
}