package com.tkachev.adboard.security.impl;

import com.tkachev.adboard.entity.User;
import com.tkachev.adboard.entity.UserStatus;
import com.tkachev.adboard.security.SecurityUserFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUserFactoryImpl implements SecurityUserFactory {
    public UserDetails fromUser(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getStatus().equals(UserStatus.ACTIVE),
                user.getStatus().equals(UserStatus.ACTIVE),
                user.getStatus().equals(UserStatus.ACTIVE),
                user.getStatus().equals(UserStatus.ACTIVE),
                user.getRole().getAuthorities()
        );
    }
}
