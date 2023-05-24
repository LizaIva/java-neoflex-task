package ru.neoflex.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.neoflex.statistics.model.Statistics;
import ru.neoflex.statistics.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Integer> {
    @Query(name = "find_uniq_stat_view", nativeQuery = true)
    List<ViewStats> findStatisticsByTimeAndUnique(@Param("start") LocalDateTime start,
                                                  @Param("end") LocalDateTime end,
                                                  @Param("skipUrisCheck") Integer skipUrisCheck,
                                                  @Param("uris") List<String> uris);

    @Query(
            "select new ru.neoflex.statistics.model.ViewStats(s.app, s.uri, count(s.uri)) " +
                    "from Statistics s " +
                    "where s.created > :start " +
                    "and s.created < :end " +
                    "and (:skipUrisCheck = 1 or s.uri in (:uris)) " +
                    "group by s.app, s.uri " +
                    "order by count(s.uri) desc "
    )
    List<ViewStats> findStatisticsByTime(@Param("start") LocalDateTime start,
                                         @Param("end") LocalDateTime end,
                                         @Param("skipUrisCheck") Integer skipUrisCheck,
                                         @Param("uris") List<String> uris);
}
