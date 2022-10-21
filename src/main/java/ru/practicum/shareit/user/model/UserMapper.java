package ru.practicum.shareit.user.model;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;

@Component
public class UserMapper {
    public User ToUser(UserDto userDto) {
        return new User(userDto.getName(), userDto.getEmail());
    }

    public UserDto ToUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
}
