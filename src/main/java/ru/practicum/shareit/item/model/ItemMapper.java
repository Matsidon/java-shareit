package ru.practicum.shareit.item.model;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.model.User;

@Component
public class ItemMapper {
    public Item ToItem (ItemDto itemDto, User user) {
        return new Item(itemDto.getName(), itemDto.getDescription(), itemDto.getAvailable(), user);
    }

    public ItemDto ToItemDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getAvailable());
    }
}
