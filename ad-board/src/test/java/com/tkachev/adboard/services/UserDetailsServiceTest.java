package com.tkachev.adboard.services;

import com.tkachev.adboard.config.TestConfig;
import com.tkachev.adboard.entity.User;
import com.tkachev.adboard.exception_handling.exceptions.EntityAlreadyExistsException;
import com.tkachev.adboard.repositories.UserRepository;
import com.tkachev.adboard.security.JwtTokenProvider;
import com.tkachev.adboard.security.SecurityUserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest
class UserDetailsServiceTest {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private SecurityUserFactory securityUserFactory;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    private void beforeEach() {
        reset(userRepository, securityUserFactory);
    }

    @Test
    void loadUserByUsernameTest() {
        String username = "test@gmail.com";
        when(securityUserFactory.fromUser(any(User.class))).thenReturn(mock(UserDetails.class));
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(new User()));

        userDetailsService.loadUserByUsername(username);

        verify(securityUserFactory).fromUser(any(User.class));
        verify(userRepository).findUserByEmail(username);
    }

    @Test
    void throwExceptionIfUserDoesNotExistWhenLoadingUserByUsername() {
        String username = "test@gmail.com";
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));
    }
}