package ru.neoflex.statistics.service;

import ru.neoflex.statistics.dto.StatisticsDto;
import ru.neoflex.statistics.dto.ViewStatsDto;

import java.util.List;

public interface StatisticsService {
    StatisticsDto put(StatisticsDto statisticsDto);

    List<ViewStatsDto> get(String start, String end, List<String> uris, Boolean unique);
}
