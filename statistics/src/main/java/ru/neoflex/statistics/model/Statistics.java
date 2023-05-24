package ru.neoflex.statistics.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedNativeQuery(
        name = "find_uniq_stat_view",
        query = "select s.app, s.uri, count(s.uri) as hits " +
                "from (select distinct on (s.ip) s.* from STATISTICS s " +
                "           where s.created > :start " +
                "           and s.created < :end " +
                "           and (:skipUrisCheck = 1 or s.uri in (:uris))) as s " +
                "group by s.app, s.uri " +
                "order by count(s.uri) desc ",
        resultSetMapping = "uniq_stat_view"
)
@SqlResultSetMapping(
        name = "uniq_stat_view",
        classes = @ConstructorResult(
                targetClass = ViewStats.class,
                columns = {
                        @ColumnResult(name = "app", type = String.class),
                        @ColumnResult(name = "uri", type = String.class),
                        @ColumnResult(name = "hits", type = Long.class)
                }
        )
)
public class Statistics {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "app", nullable = false)
    private String app;
    @Column(name = "uri", nullable = false)
    private String uri;
    @Column(name = "ip", nullable = false)
    private String ip;
    @Column(name = "created", insertable = false, updatable = false)
    private LocalDateTime created;

    @Transient
    private Long hits;
}
