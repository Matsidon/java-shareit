package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    Map<Long, User> userMap = new HashMap<>();
    long id;

    private long generateId() {
        return ++id;
    }

    public User createUser(User user) {
        user.setId(generateId());
        userMap.put(user.getId(), user);
        return user;
    }

    public User updateUser(long userId, User user) {
        return userMap.put(userId, user);
    }

    public User getUser(long userId) {
        return userMap.get(userId);
    }

    public User removeUser(long userId) {
        return userMap.remove(userId);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }
}
