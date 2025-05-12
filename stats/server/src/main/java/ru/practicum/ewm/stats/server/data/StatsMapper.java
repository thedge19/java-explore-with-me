package ru.practicum.ewm.stats.server.data;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.stats.dto.EndpointHitDto;

@UtilityClass
public class StatsMapper {
    public EndpointHitDto toDto(EndpointHit entity) {
        return EndpointHitDto.builder()
                .app(entity.getApp())
                .uri(entity.getUri())
                .ip(entity.getIp())
                .hitTimestamp(entity.getHitTimestamp())
                .build();
    }

    public EndpointHit fromDto(EndpointHitDto dto) {
        EndpointHit entity = new EndpointHit();
        entity.setApp(dto.getApp());
        entity.setUri(dto.getUri());
        entity.setIp(dto.getIp());
        entity.setHitTimestamp(dto.getHitTimestamp());
        return entity;
    }
}
