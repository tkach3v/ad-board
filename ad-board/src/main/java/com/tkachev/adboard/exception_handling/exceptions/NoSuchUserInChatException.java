package com.tkachev.adboard.exception_handling.exceptions;

public class NoSuchUserInChatException extends RuntimeException {
    public NoSuchUserInChatException(String message) {
        super(message);
    }
}
