package ru.neoflex.user_storage.service.user;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.neoflex.user_storage.dto.user.CreateUserDto;
import ru.neoflex.user_storage.dto.user.UserDto;

import java.util.List;

public interface UserService {

    String HELLO = "Hello";

    String BYE = "Bye";

    String GOOD_BYE = "Goodbye";

    UserDto put(CreateUserDto createUserDto);

    List<UserDto> getAll(List<Integer> ids, Integer from, Integer size);

    UserDto getUserById(Integer userId);

    void delete(Integer userId);

    String sayHello();

    Mono<String> sayBye();

    Mono<String> sayGoodByeWithDelay();

    Mono<String> concatTwoMethod();

    Flux<String> concatFiveMethod();

    Flux<String> methodWithWEbClient();
}
