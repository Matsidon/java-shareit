package ru.practicum.shareit.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.exception.UserConflictException;
import ru.practicum.shareit.user.exception.UserExistsException;
import ru.practicum.shareit.user.exception.UserValidationException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapper;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        validateEmailUser(userDto);
        User user = userMapper.toUser(userDto);
        log.info("Создан пользователь: {}", userDto);
        return userMapper.toUserDto(userRepository.createUser(user));
    }

    @Override
    public UserDto updateUser(long userId, UserDto userDto) {
        checkUserExists(userId);
        User user = userRepository.getUser(userId);
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            validateEmailUser(userDto);
            user.setEmail(userDto.getEmail());
        }
        log.info("Обновлен пользователь: {}", userDto);
        return userMapper.toUserDto(userRepository.updateUser(userId, user));
    }

    @Override
    public UserDto getUser(long userId) {
        checkUserExists(userId);
        log.info("Получен пользователь: {}", userId);
        return userMapper.toUserDto(userRepository.getUser(userId));
    }

    @Override
    public UserDto removeUser(long userId) {
        checkUserExists(userId);
        log.info("Удалён пользователь: {}", userId);
        return userMapper.toUserDto(userRepository.removeUser(userId));
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userRepository.getAllUsers()) {
            userDtoList.add(userMapper.toUserDto(user));
        }
        log.info("Получен список всех пользователей");
        return userDtoList;
    }

    @Override
    public void checkUserExists(long userId) {
        if (userRepository.getUser(userId) == null) {
            log.info("Пользователя с id = {} нет", userId);
            throw new UserExistsException("Пользователя с id = " + userId + "нет");
        }
    }

    private void validateEmailUser(UserDto userDto) {
        if (!userDto.getEmail().contains("@")) {
            log.info("Некорректное значение поля email = {}", userDto.getEmail());
            throw new UserValidationException("Некорректное значение поля email = " + userDto.getEmail());
        }
        List<String> emails = new ArrayList<>();
        for (UserDto userDto1 : getAllUsers()) {
            emails.add(userDto1.getEmail());
        }
        if (emails.contains(userDto.getEmail())) {
            log.info("Пользователь с полем email = {} уже существует", userDto.getEmail());
            throw new UserConflictException("Пользователь с полем email = " + userDto.getEmail() + " уже существует");
        }
    }
}
