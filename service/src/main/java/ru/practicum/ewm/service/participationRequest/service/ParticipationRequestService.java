package ru.practicum.ewm.service.participationRequest.service;

import ru.practicum.ewm.service.participationRequest.dto.ParticipationRequestDto;

import java.util.List;

public interface ParticipationRequestService {

    List<ParticipationRequestDto> getAll(long userId) ;


    ParticipationRequestDto create(long userId, long eventId);


    ParticipationRequestDto patch(long userId, long requestId);
}
