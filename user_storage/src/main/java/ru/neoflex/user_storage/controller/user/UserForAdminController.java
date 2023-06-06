package ru.neoflex.user_storage.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.neoflex.user_storage.client.StatsClient;
import ru.neoflex.user_storage.dto.user.CreateUserDto;
import ru.neoflex.user_storage.dto.user.UserDto;
import ru.neoflex.user_storage.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin/users")
public class UserForAdminController {
    private final UserService userService;
    private final StatsClient statsClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody @Valid CreateUserDto createUserDto) {
        log.info("Create user");
        return userService.put(createUserDto);
    }

    @GetMapping
    public List<UserDto> getAll(
            @RequestParam(name = "ids", required = false) List<Integer> ids,
            @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            HttpServletRequest request) {
        log.info("Request all users from {} size {}", from, size);
        statsClient.hitCall(request);
        return userService.getAll(ids, from, size);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer userId) {
        log.info("Delete user with id = {}", userId);
        userService.delete(userId);
    }

    @GetMapping("/hello")
    public String sayHello() {
        log.info("Say hello start");
        return userService.sayHello();
    }

    @GetMapping("/bye")
    @ResponseStatus(HttpStatus.OK)
    public Flux<String> sayBye() {
        return userService.sayBye();
    }

    @GetMapping("/bye/delay")
    @ResponseStatus(HttpStatus.OK)
    public Flux<String> sayByeWithDelay() {
        return userService.sayGoodByeWithDelay();
    }

    @GetMapping("/sayAll")
    @ResponseStatus(HttpStatus.OK)
    public Flux<String> concatTwoMethod() {
        return userService.concatTwoMethod();
    }

    @GetMapping("/sayAllDifferentWay")
    @ResponseStatus(HttpStatus.OK)
    public Flux<String> mergeMethod() {
        return userService.sayBye().mergeWith(sayByeWithDelay());
    }


    public Flux<String> methodWithWEbClient() {
        WebClient client = WebClient.create("http://localhost:5050");
        Flux<String> fluxResult = client.get()
                .uri("/bye")
                .retrieve()
                .bodyToFlux(String.class);
        return fluxResult;
    }
}
