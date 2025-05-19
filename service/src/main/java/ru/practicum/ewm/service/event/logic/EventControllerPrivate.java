package ru.practicum.ewm.service.event.logic;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.event.data.event.*;
import ru.practicum.ewm.service.participationRequest.data.ParticipationRequestDto;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}/events")
@RequiredArgsConstructor
public class EventControllerPrivate {
    private final EventService eventService;

    @GetMapping()
    public List<EventShortDto> getAll(@PathVariable long userId,
                                      @Valid @RequestParam(defaultValue = "0") @Min(0) int from,
                                      @Valid @RequestParam(defaultValue = "10") @Min(1) int size) {
        return eventService.getAllByInitiator(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getById(@PathVariable long userId,
                                @PathVariable long eventId) {
        return eventService.getByIdByInitiator(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getParticipationRequestsByInitiator(@PathVariable long userId,
                                                                             @PathVariable long eventId) {
        return eventService.getParticipationRequestsByInitiator(userId, eventId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@PathVariable long userId,
                               @Valid @RequestBody EventNewDto eventNewDto) {
        return eventService.create(userId, eventNewDto);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEventInfo(@PathVariable long userId,
                                       @PathVariable long eventId,
                                       @Valid @RequestBody EventUpdateUserRequest updateEventUserRequest) {
        return eventService.patchByInitiator(userId, eventId, updateEventUserRequest);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult patchEventRequests(@PathVariable long userId,
                                                             @PathVariable long eventId,
                                                             @Valid @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {

        return eventService.patchParticipationRequestsByInitiator(userId, eventId, eventRequestStatusUpdateRequest);
    }
}
