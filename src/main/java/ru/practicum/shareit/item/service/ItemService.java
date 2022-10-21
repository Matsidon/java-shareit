package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto createItem(ItemDto itemDto, long userId);

    ItemDto updateItem(ItemDto itemDto, long itemId, long userId);

    ItemDto getItem(long itemId);

    List<ItemDto> getAllItemsByUser(long userId);

    List<ItemDto> searchItemsByText(long userId, String text);
}
