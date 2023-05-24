package ru.neoflex.user_storage.storage.user;

import ru.neoflex.user_storage.model.user.User;

import java.util.List;

public interface UserStorage {
    String USER_NOT_FOUND = "User with id = %s not found.";

    User put(User user);

    List<User> getAll(Integer from, Integer size);

    List<User> getAllByIds(List<Integer> ids, Integer from, Integer size);

    User getById(Integer id);

    void delete(Integer userId);

    void checkUser(Integer id);
}
