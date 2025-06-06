package ru.practicum.ewm.service.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.service.category.dto.CategoryDto;
import ru.practicum.ewm.service.event.model.EventState;
import ru.practicum.ewm.service.user.dto.UserShortDto;
import ru.practicum.ewm.service.util.UtilConstants;

import java.time.LocalDateTime;

@Data
@Builder
public class EventFullDto {
    private Long id;

    private UserShortDto initiator;
    private CategoryDto category;
    private LocationDto location;

    private String title;
    private String annotation;
    private String description;
    private EventState state;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = UtilConstants.DATETIME_FORMAT)
    private LocalDateTime eventDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = UtilConstants.DATETIME_FORMAT)
    private LocalDateTime createdOn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = UtilConstants.DATETIME_FORMAT)
    private LocalDateTime publishedOn;

    private Integer participantLimit;
    private Boolean paid;
    private Boolean requestModeration;

    private Long confirmedRequests;
    private Long views;
}
