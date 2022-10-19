package com.tkachev.adboard.services.impl;

import com.tkachev.adboard.repositories.UserRepository;
import com.tkachev.adboard.security.SecurityUserFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final SecurityUserFactory securityUserFactory;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return securityUserFactory.fromUser(userRepository.findUserByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException("User with email = " + email + " doesn't exist")));
    }
}
