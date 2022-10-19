package com.tkachev.adboard.exception_handling.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JwtAuthenficationException extends AuthenticationException {

    private final HttpStatus httpStatus;

    public JwtAuthenficationException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }
}
