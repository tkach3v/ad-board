package com.tkachev.adboard.security;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface JwtTokenProvider {

    String createToken(String username, String role);

    boolean validateToken(String token);

    Authentication getAuthentication(String token);

    String getUsername(String token);

    String resolveToken(HttpServletRequest request);
}
