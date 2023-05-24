package ru.neoflex.user_storage.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.neoflex.user_storage.dto.stats.StatisticsDto;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatsClient {

    private final ObjectMapper objectMapper;

    @Value("${spring.application.name}")
    private String serviceName;
    @Value("${service.stats.hit.url}")
    private String statsServiceUrl;

    @SneakyThrows
    public void hitCall(HttpServletRequest request) {
        StatisticsDto requestBody = StatisticsDto.builder()
                .app(serviceName)
                .ip(request.getRemoteHost())
                .uri(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        HttpRequest clientRequest = HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString(objectMapper.writeValueAsString(requestBody), StandardCharsets.UTF_8))
                .uri(new URI(statsServiceUrl))
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build();

        log.info("|-- Request to stats service {}", clientRequest.toString());

        HttpClient.newBuilder().build()
                .send(clientRequest, HttpResponse.BodyHandlers.ofString());
    }
}
