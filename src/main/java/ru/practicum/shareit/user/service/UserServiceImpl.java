package ru.practicum.shareit.user.service;

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
        return userMapper.toUserDto(userRepository.updateUser(userId, user));
    }

    @Override
    public UserDto getUser(long userId) {
        checkUserExists(userId);
        return userMapper.toUserDto(userRepository.getUser(userId));
    }

    @Override
    public UserDto removeUser(long userId) {
        checkUserExists(userId);
        return userMapper.toUserDto(userRepository.removeUser(userId));
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userRepository.getAllUsers()) {
            userDtoList.add(userMapper.toUserDto(user));
        }
        return userDtoList;
    }

    @Override
    public void checkUserExists(long userId) {
        if (userRepository.getUser(userId) == null) {
            throw new UserExistsException("Пользователя с id = " + userId + "нет");
        }
    }

    private void validateEmailUser(UserDto userDto) {
        if (!userDto.getEmail().contains("@")) {
            throw new UserValidationException("Некорректное значение поля email = " + userDto.getEmail());
        }
        List<String> emails = new ArrayList<>();
        for (UserDto userDto1 : getAllUsers()) {
            emails.add(userDto1.getEmail());
        }
        if (emails.contains(userDto.getEmail())) {
            throw new UserConflictException("Пользователь с полем email = " + userDto.getEmail() + " уже существует");
        }
    }
}
