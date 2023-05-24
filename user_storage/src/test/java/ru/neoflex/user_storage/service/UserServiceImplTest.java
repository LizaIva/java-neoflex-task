package ru.neoflex.user_storage.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.neoflex.user_storage.dto.user.UserDto;
import ru.neoflex.user_storage.exception.UnknownDataException;
import ru.neoflex.user_storage.model.user.User;
import ru.neoflex.user_storage.service.user.impl.UserServiceImpl;
import ru.neoflex.user_storage.storage.user.UserStorage;
import ru.neoflex.user_storage.utils.user.UserMapper;


import javax.validation.ValidationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserStorage userStorage;
    @Spy
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void putAndGetById() {
        User user = new User();
        user.setId(1);
        user.setName("Liza");
        user.setEmail("iva@mail.ru");

        Mockito.when(userStorage.getById(user.getId())).thenReturn(user);

        UserDto actualUserDto = userService.getUserById(user.getId());

        assertEquals(1, actualUserDto.getId());
        assertEquals("Liza", actualUserDto.getName());
        assertEquals("iva@mail.ru", actualUserDto.getEmail());
    }

    @Test
    void putWithWrongEmailAndGetById() {
        User user = new User();
        user.setId(1);
        user.setName("Liza");
        user.setEmail("ivamail.ru");

        Mockito.when(userStorage.put(user)).thenThrow(ValidationException.class);

        assertThrows(ValidationException.class, () -> userStorage.put(user));
    }

    @Test
    void putWithEmptyEmailAndGetById() {
        User user = new User();
        user.setId(1);
        user.setName("Liza");
        user.setEmail("");

        Mockito.when(userStorage.put(user)).thenThrow(ValidationException.class);

        assertThrows(ValidationException.class, () -> userStorage.put(user));
    }


    @Test
    void getAll() {

        User user = new User();
        user.setId(1);
        user.setName("Liza");
        user.setEmail("iva@mail.ru");

        List<User> users = List.of(user);
        Mockito.when(userStorage.getAll(null, null)).thenReturn(users);

        List<UserDto> actual = userService.getAll(null, null, null);

        assertEquals(1, actual.size());

        UserDto actualUserDto = actual.get(0);

        assertEquals(1, actualUserDto.getId());
        assertEquals("Liza", actualUserDto.getName());
        assertEquals("iva@mail.ru", actualUserDto.getEmail());


        User user2 = new User();
        user2.setId(2);
        user2.setName("Masha");
        user2.setEmail("petrova@mail.ru");

        List<User> users2 = List.of(user, user2);
        Mockito.when(userStorage.getAll(null, null)).thenReturn(users2);

        List<UserDto> actual2 = userService.getAll(null, null, null);

        assertEquals(2, actual2.size());

        UserDto actualUserDto2 = actual2.get(1);

        assertEquals(2, actualUserDto2.getId());
        assertEquals("Masha", actualUserDto2.getName());
        assertEquals("petrova@mail.ru", actualUserDto2.getEmail());
    }

    @Test
    void getByIdUnknownUser() {
        User user = new User();
        user.setId(1);
        user.setName("Liza");
        user.setEmail("iva@mail.ru");

        Mockito.when(userStorage.getById(user.getId())).thenReturn(user);

        UserDto actualUserDto = userService.getUserById(user.getId());

        assertEquals(1, actualUserDto.getId());
        assertEquals("Liza", actualUserDto.getName());
        assertEquals("iva@mail.ru", actualUserDto.getEmail());

        Mockito.when(userStorage.getById(99)).thenThrow(UnknownDataException.class);
        assertThrows(UnknownDataException.class, () -> userService.getUserById(99));
    }
}