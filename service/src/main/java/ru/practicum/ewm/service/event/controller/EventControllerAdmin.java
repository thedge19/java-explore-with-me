package ru.practicum.ewm.service.event.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.event.dto.EventFullDto;
import ru.practicum.ewm.service.event.dto.EventUpdateAdminRequest;
import ru.practicum.ewm.service.event.model.EventState;
import ru.practicum.ewm.service.event.service.EventService;
import ru.practicum.ewm.service.util.UtilConstants;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class EventControllerAdmin {
    private final EventService eventService;

    @GetMapping()
    public List<EventFullDto> get(@RequestParam(required = false) List<Long> users,
                                  @RequestParam(required = false) List<EventState> states,
                                  @RequestParam(required = false) List<Long> categories,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = UtilConstants.DATETIME_FORMAT) LocalDateTime rangeStart,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = UtilConstants.DATETIME_FORMAT) LocalDateTime rangeEnd,
                                  @RequestParam(defaultValue = "0") @Min(0) int from,
                                  @RequestParam(defaultValue = "10") @Min(1) int size) {
        return eventService.getAllByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patch(@PathVariable long eventId,
                              @Valid @RequestBody EventUpdateAdminRequest updateEventAdminRequest) {
        return eventService.patchByAdmin(eventId, updateEventAdminRequest);
    }
}
