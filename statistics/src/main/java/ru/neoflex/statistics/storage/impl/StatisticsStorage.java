package ru.neoflex.statistics.storage.impl;

import ru.neoflex.statistics.model.Statistics;
import ru.neoflex.statistics.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsStorage {
    Statistics put(Statistics statistics);

    List<ViewStats> get(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
