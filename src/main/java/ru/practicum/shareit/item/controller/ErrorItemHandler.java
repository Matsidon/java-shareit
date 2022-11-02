package ru.practicum.shareit.item.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.item.exception.ItemUpdateException;
import ru.practicum.shareit.user.controller.ErrorUserResponse;
import ru.practicum.shareit.user.exception.UserExistsException;

@RestControllerAdvice("ru.practicum.shareit.item.controller")
public class ErrorItemHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorUserResponse handlerUpdateException(final ItemUpdateException e) {
        return new ErrorUserResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorUserResponse handlerExistsException(final UserExistsException e) {
        return new ErrorUserResponse(e.getMessage());
    }
}
