package com.enershare.exception;

import lombok.Getter;

@Getter
public class AuthenticationException extends RuntimeException {
    private final int code;


    public AuthenticationException(String message, int code) {
        super(message);
        this.code = code;
    }

    public AuthenticationException(String message, int code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
