package ru.practicum.shareit.item.controller;

public class ErrorItemResponse extends RuntimeException{
    public ErrorItemResponse(String message) {
        super(message);
    }
}
