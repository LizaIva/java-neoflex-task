package ru.neoflex.statistics.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.statistics.dto.StatisticsDto;
import ru.neoflex.statistics.dto.ViewStatsDto;
import ru.neoflex.statistics.service.StatisticsService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class StatisticsController {
    private final StatisticsService statisticsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public StatisticsDto create(@RequestBody @Valid StatisticsDto statisticsDto) {
        log.info("Создание действия для статистики");
        return statisticsService.put(statisticsDto);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> get(@RequestParam String start,
                                  @RequestParam String end,
                                  @RequestParam(required = false) List<String> uris,
                                  @RequestParam(name = "unique", required = false, defaultValue = "false") Boolean unique) {
        log.info("Запрос статистики");
        return statisticsService.get(start, end, uris, unique);
    }
}
