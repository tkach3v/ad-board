package com.tkachev.adboard.services.impl;

import com.tkachev.adboard.dto.models.auth.AuthenticationRequest;
import com.tkachev.adboard.dto.models.auth.AuthenticationResponse;
import com.tkachev.adboard.entity.User;
import com.tkachev.adboard.exception_handling.exceptions.JwtAuthenficationException;
import com.tkachev.adboard.repositories.UserRepository;
import com.tkachev.adboard.security.JwtTokenProvider;
import com.tkachev.adboard.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authData) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authData.getEmail(), authData.getPassword()));
            User user = userRepository.findUserByEmail(authData.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User with email = " + authData.getEmail() + " doesn't exist"));

            String token = jwtTokenProvider.createToken(authData.getEmail(), user.getRole().name());

            log.info("User with email=\"{}\" is authorized and has token={}", authData.getEmail(), token);

            return new AuthenticationResponse(
                    authData.getEmail(),
                    token
            );
        } catch (AuthenticationException e) {
            log.warn("Invalid email/password combination");
            throw new JwtAuthenficationException("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }
}
