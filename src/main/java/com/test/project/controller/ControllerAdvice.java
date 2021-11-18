package com.test.project.controller;

import com.test.project.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    private final static Logger LOG = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String badRequest(BadRequestException exception) {
        LOG.info("BadRequestException: {}", exception.getMessage(), exception);
        return exception.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String methodArgumentNotValid(MethodArgumentNotValidException exception) {
        LOG.info("MethodArgumentNotValidException: {}", exception.getMessage(), exception);
        return exception.getMessage();
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String unAuthorized(UnAuthorizedException exception) {
        LOG.info("UnAuthorizedException: {}", exception.getMessage(), exception);
        return exception.getMessage();
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String forbidden(ForbiddenException exception) {
        LOG.info("ForbiddenException: {}", exception.getMessage(), exception);
        return exception.getMessage();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound(NotFoundException exception) {
        LOG.info("NotFoundException: {}", exception.getMessage(), exception);
        return exception.getMessage();
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String conflict(ConflictException exception) {
        LOG.info("ConflictException: {}", exception.getMessage(), exception);
        return exception.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String unexpected(Exception exception) {
        LOG.info("Exception: {}", exception.getMessage(), exception);
        return exception.getMessage();
    }
}
