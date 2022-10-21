package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ItemRepository {
    Map<Long, Item> itemMap = new HashMap<>();
    private long id;

    private long generateId() {
        return ++id;
    }

    public Item createItem(Item item) {
        item.setId(generateId());
        itemMap.put(item.getId(), item);
        return item;
    }

    public Item updateItem(Item item) {
        itemMap.put(item.getId(), item);
        return item;
    }

    public Item getItem(long itemId) {
        return itemMap.get(itemId);
    }


    public List<Item> getAllItemsByUser(long userId) {
        return itemMap.values().stream().filter(item -> item.getOwner().getId() == userId).collect(Collectors.toList());
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(itemMap.values());
    }
}
