package ru.practicum.shareit.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.exception.ItemUpdateException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper;
    private final UserService userService;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository, ItemMapper itemMapper,
                           UserService userService) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.itemMapper = itemMapper;
        this.userService = userService;
    }

    @Override
    public ItemDto createItem(ItemDto itemDto, long userId) {
        userService.checkUserExists(userId);
        User user = userRepository.getUser(userId);
        Item item = itemMapper.toItem(itemDto, user);
        log.info("Создана вещь: {} пользователя с id = {}", itemDto, userId);
        return itemMapper.toItemDto(itemRepository.createItem(item));
    }

    @Override
    public ItemDto updateItem(ItemDto itemDto, long itemId, long userId) {
        userService.checkUserExists(userId);
        checkItemExists(itemId);
        checkItemByOwner(itemId, userId);
        Item item = itemRepository.getItem(itemId);
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        log.info("Обновлена вещь: {} пользователя с id = {}", itemDto, userId);
        return itemMapper.toItemDto(itemRepository.updateItem(item));
    }

    @Override
    public ItemDto getItem(long itemId) {
        checkItemExists(itemId);
        log.info("Получена вещь с id = {}", itemId);
        return itemMapper.toItemDto(itemRepository.getItem(itemId));
    }

    @Override
    public List<ItemDto> getAllItemsByUser(long userId) {
        userService.checkUserExists(userId);
        List<ItemDto> itemDtoList = new ArrayList<>();
        for (Item item : itemRepository.getAllItemsByUser(userId)) {
            itemDtoList.add(itemMapper.toItemDto(item));
        }
        log.info("Получен список вещей пользователя с id = {}", userId);
        return itemDtoList;
    }

    private List<ItemDto> getAllItems() {
        log.info("Получен список всех вещей");
        return itemRepository.getAllItems().stream().map(itemMapper::toItemDto).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> searchItemsByText(long userId, String text) {
        Set<ItemDto> itemsBySearch = new HashSet<>();
        if (text.isEmpty()) {
            return new ArrayList<>();
        }
        getAllItems().stream()
                .filter(itemDto -> itemDto.getName().toLowerCase().contains(text.toLowerCase())
                        && itemDto.getAvailable().equals(true))
                .forEach(itemsBySearch::add);
        getAllItems().stream()
                .filter(itemDto -> itemDto.getDescription().toLowerCase().contains(text.toLowerCase())
                        && itemDto.getAvailable().equals(true))
                .forEach(itemsBySearch::add);
        log.info("Получен список вещей пользователя с id = {} по строке = {}", userId, text);
        return new ArrayList<>(itemsBySearch);
    }

    private void checkItemExists(long itemId) {
        if (itemRepository.getItem(itemId) == null) {
            log.info("Вещь с id = {} не существует", itemId);
            throw new ItemUpdateException("Вещь с id = " + itemId + " не существует");
        }
    }

    private void checkItemByOwner(long itemId, long userId) {
        int sizeList = itemRepository.getAllItemsByUser(userId).stream()
                .filter(item -> item.getId() == itemId)
                .collect(Collectors.toList())
                .size();
        if (sizeList == 0) {
            log.info("Вещь с id = {} не существует для пользователя с id = {}", itemId, userId);
            throw new ItemUpdateException("Вещь с id = " + itemId + " не существует для пользователя с id = " + userId);
        }
    }
}
