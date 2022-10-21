package ru.practicum.shareit.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.user.exception.UserConflictException;
import ru.practicum.shareit.user.exception.UserValidationException;

@RestControllerAdvice("ru.practicum.shareit.user.controller")
public class ErrorUserHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorUserResponse handlerConflictException(final UserConflictException e) {
        return new ErrorUserResponse(e.getMessage());
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorUserResponse handlerValidationException(final UserValidationException e) {
        return new ErrorUserResponse(e.getMessage());
    }
}
