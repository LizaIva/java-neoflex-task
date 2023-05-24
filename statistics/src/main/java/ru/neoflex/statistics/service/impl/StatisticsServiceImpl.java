package ru.neoflex.statistics.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.neoflex.statistics.dto.StatisticsDto;
import ru.neoflex.statistics.dto.ViewStatsDto;
import ru.neoflex.statistics.model.Statistics;
import ru.neoflex.statistics.service.StatisticsService;
import ru.neoflex.statistics.model.ViewStats;
import ru.neoflex.statistics.storage.impl.StatisticsStorage;
import ru.neoflex.statistics.utils.StatisticsMapper;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private static final DateTimeFormatter START_END_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final StatisticsStorage statisticsStorage;
    private final StatisticsMapper statisticsMapper;

    @Override
    public StatisticsDto put(StatisticsDto statisticsDto) {
        Statistics actualStatistics = statisticsStorage.put(statisticsMapper.mapToStatistics(statisticsDto));
        return statisticsMapper.mapToStatisticsDto(actualStatistics);
    }

    @Override
    public List<ViewStatsDto> get(String start, String end, List<String> uris, Boolean unique) {
        LocalDateTime decodedStart = decodeDateTime(start);
        LocalDateTime decodedEnd = decodeDateTime(end);

        if (uris != null && uris.isEmpty()) {
            uris = Collections.emptyList();
        }

        List<ViewStats> viewStats = statisticsStorage.get(decodedStart, decodedEnd, uris, unique);
        return statisticsMapper.mapToViewStatsDtos(viewStats);
    }

    private static LocalDateTime decodeDateTime(String dateTime) {
        return LocalDateTime.parse(URLDecoder.decode(dateTime, StandardCharsets.UTF_8), START_END_DATE_FORMATTER);
    }
}
