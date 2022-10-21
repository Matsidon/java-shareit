package ru.practicum.shareit.user.controller;

import lombok.Data;

@Data
public class ErrorUserResponse {

    private final String description;

    public ErrorUserResponse(String description) {
        this.description = "error: " + description;
    }
}
