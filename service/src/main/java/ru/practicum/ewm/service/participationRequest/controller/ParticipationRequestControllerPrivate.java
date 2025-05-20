package ru.practicum.ewm.service.participationRequest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.participationRequest.dto.ParticipationRequestDto;
import ru.practicum.ewm.service.participationRequest.service.ParticipationRequestService;

import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/requests")
@RequiredArgsConstructor
public class ParticipationRequestControllerPrivate {
    private final ParticipationRequestService participationRequestService;

    @GetMapping()
    public List<ParticipationRequestDto> getAll(@PathVariable long userId) {
        return participationRequestService.getAll(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto create(@PathVariable long userId,
                                          @RequestParam long eventId) {
        return participationRequestService.create(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto patch(@PathVariable long userId,
                                         @PathVariable long requestId) {
        return participationRequestService.patch(userId, requestId);
    }
}
