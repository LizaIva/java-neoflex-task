package ru.neoflex.user_storage.utils.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.neoflex.user_storage.model.user.User;
import ru.neoflex.user_storage.dto.user.CreateUserDto;
import ru.neoflex.user_storage.dto.user.UserDto;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public User mapToUser(CreateUserDto createUser) {
        return User.builder()
                .name(createUser.getName())
                .email(createUser.getEmail())
                .build();
    }

    public List<UserDto> mapToUsersDto(List<User> users) {
        List<UserDto> usersDto = new ArrayList<>();
        for (User user : users) {
            usersDto.add(mapToUserDto(user));
        }
        return usersDto;
    }
}
