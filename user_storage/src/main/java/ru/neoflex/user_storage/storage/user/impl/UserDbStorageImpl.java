package ru.neoflex.user_storage.storage.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ru.neoflex.user_storage.exception.UnknownDataException;
import ru.neoflex.user_storage.model.user.User;
import ru.neoflex.user_storage.repository.user.UserRepository;
import ru.neoflex.user_storage.storage.user.UserStorage;

import java.util.List;

@Component("userDbStorageImpl")
@RequiredArgsConstructor
public class UserDbStorageImpl implements UserStorage {

    private final UserRepository userRepository;

    @Override
    public User put(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public List<User> getAll(Integer from, Integer size) {
        return userRepository.findAll(PageRequest.of(from, size)).getContent();
    }

    @Override
    public List<User> getAllByIds(List<Integer> ids, Integer from, Integer size) {
        return userRepository.findAllByIdIn(ids, PageRequest.of(from, size));
    }

    @Override
    public User getById(Integer id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public void delete(Integer userId) {
        checkUser(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public void checkUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new UnknownDataException(String.format(USER_NOT_FOUND, id));
        }
    }
}
