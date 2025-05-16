package ru.practicum.ewm.stats.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import ru.practicum.ewm.stats.dto.EndpointHitDto;
import ru.practicum.ewm.stats.dto.ViewStatsDto;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class StatsClient {
    private final WebClient webClient;

    public StatsClient(String serverUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(serverUrl)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .responseTimeout(Duration.ofSeconds(3))
                ))
                .build();
    }

    public List<ViewStatsDto> getStats(String start, String end, List<String> uris, Boolean unique) {
        try {
            String urisAsString = String.join(",", uris);

            return webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/stats")
                            .queryParam("start", start)
                            .queryParam("end", end)
                            .queryParam("uris", urisAsString)
                            .queryParam("unique", unique)
                            .build())
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response -> {
                        log.error("Error from stats-service: {}", response.statusCode());
                        return Mono.error(new RuntimeException("Stats service unavailable"));
                    })
                    .bodyToFlux(ViewStatsDto.class)
                    .collectList()
                    .block(Duration.ofSeconds(2));
        } catch (WebClientResponseException | IllegalStateException e) {
            log.warn("Failed to get stats from stats-service: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public void createEndpointHit(EndpointHitDto endpointHitDto) {
        try {
            webClient.post()
                    .uri("/hit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(endpointHitDto))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response -> {
                        log.error("Error posting hit to stats-service: {}", response.statusCode());
                        return Mono.empty();
                    })
                    .toBodilessEntity()
                    .block(Duration.ofSeconds(2));
        } catch (Exception e) {
            log.warn("Failed to post hit to stats-service: {}", e.getMessage());
        }
    }
}