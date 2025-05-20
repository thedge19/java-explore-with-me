package ru.practicum.ewm.service.event.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.ewm.service.event.controller.EventControllerPublic;
import ru.practicum.ewm.service.event.dto.*;
import ru.practicum.ewm.service.event.model.EventState;
import ru.practicum.ewm.service.participationRequest.dto.ParticipationRequestDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<EventFullDto> getAllByAdmin(List<Long> users,
                                            List<EventState> states,
                                            List<Long> categories,
                                            LocalDateTime rangeStart,
                                            LocalDateTime rangeEnd,
                                            int from,
                                            int size);

    List<EventShortDto> getAllByInitiator(long userId, int from, int size);

    EventFullDto getByIdByInitiator(long userId, long eventId) ;

    List<ParticipationRequestDto> getParticipationRequestsByInitiator(long userId, long eventId);

    List<EventShortDto> getAllPublic(String text,
                                            List<Long> categories,
                                            Boolean paid,
                                            LocalDateTime rangeStart,
                                            LocalDateTime rangeEnd,
                                            boolean onlyAvailable,
                                            EventControllerPublic.SortMode sort,
                                            int from,
                                            int size,
                                            HttpServletRequest request);

    EventFullDto getByIdPublic(long eventId, HttpServletRequest request);

    EventFullDto create(long userId, EventNewDto eventNewDto);

    EventFullDto patchByAdmin(long eventId, EventUpdateAdminRequest updateEventAdminRequest);

    EventFullDto patchByInitiator(long userId, long eventId, EventUpdateUserRequest updateEventUserRequest);

    EventRequestStatusUpdateResult patchParticipationRequestsByInitiator(@PathVariable long userId,
                                                                                @PathVariable long eventId,
                                                                                @Valid @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);
}
