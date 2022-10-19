package com.tkachev.adboard.security;

import com.tkachev.adboard.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface SecurityUserFactory {

    UserDetails fromUser(User user);
}
