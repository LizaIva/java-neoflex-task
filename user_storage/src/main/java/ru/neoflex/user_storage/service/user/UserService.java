package ru.neoflex.user_storage.service.user;

import ru.neoflex.user_storage.dto.user.CreateUserDto;
import ru.neoflex.user_storage.dto.user.UserDto;

import java.util.List;

public interface UserService {
    UserDto put(CreateUserDto createUserDto);

    List<UserDto> getAll(List<Integer> ids, Integer from, Integer size);

    UserDto getUserById(Integer userId);

    void delete(Integer userId);
}
