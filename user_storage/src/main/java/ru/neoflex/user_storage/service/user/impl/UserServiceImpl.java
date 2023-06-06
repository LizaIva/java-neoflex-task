package ru.neoflex.user_storage.service.user.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import ru.neoflex.user_storage.dto.user.CreateUserDto;
import ru.neoflex.user_storage.dto.user.UserDto;
import ru.neoflex.user_storage.model.user.User;
import ru.neoflex.user_storage.service.user.UserService;
import ru.neoflex.user_storage.storage.user.UserStorage;
import ru.neoflex.user_storage.utils.user.UserMapper;

import java.time.Duration;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    private final UserMapper userMapper;

    Scheduler scheduler = Schedulers.newBoundedElastic(5, 10, "MyThreadGroup");

    @Override
    public UserDto put(CreateUserDto createUserDto) {
        User createUser = userMapper.mapToUser(createUserDto);
        User actualUser = userStorage.put(createUser);
        log.info("Create user with id{}", actualUser.getId());
        return userMapper.mapToUserDto(actualUser);
    }

    public UserDto getUserById(Integer userId) {
        return userMapper.mapToUserDto(userStorage.getById(userId));
    }

    @Override
    public List<UserDto> getAll(List<Integer> ids, Integer from, Integer size) {
        List<User> users;
        if (ids != null && !ids.isEmpty()) {
            users = userStorage.getAllByIds(ids, from, size);
        } else {
            users = userStorage.getAll(from, size);
        }
        return userMapper.mapToUsersDto(users);
    }

    @Override
    public void delete(Integer userId) {
        log.info("Delete user with id = {}", userId);
        userStorage.delete(userId);
    }

    @Override
    public String sayHello() {
        return HELLO;
    }

    @Override
    public Flux<String> sayBye() {
        return Flux.just(BYE);
    }

    @Override
    public Flux<String> sayGoodByeWithDelay() {
        return Flux.just(GOOD_BYE)
                .delayElements(Duration.ofSeconds(5), scheduler);
    }

    @Override
    public Flux<String> concatTwoMethod() {
        return Flux.concat(sayBye(), sayGoodByeWithDelay());
    }

    public Flux<String> concatFiveMethod() {
        return Flux.merge(sayGoodByeWithDelay(), sayGoodByeWithDelay(), sayGoodByeWithDelay(), sayGoodByeWithDelay(), sayGoodByeWithDelay())
                .parallel()
                .runOn(Schedulers.elastic())
                .sequential();
    }

}
