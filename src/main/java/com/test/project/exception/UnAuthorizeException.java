package com.test.project.exception;

public class UnAuthorizeException extends RuntimeException {
    public UnAuthorizeException(String message) {
        super(message);
    }
}
