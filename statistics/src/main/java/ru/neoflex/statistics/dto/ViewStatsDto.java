package ru.neoflex.statistics.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewStatsDto {
    @NotEmpty
    private String app;
    @NotEmpty
    private String uri;
    @NotNull
    private Long hits;
}
