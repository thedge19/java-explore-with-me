package ru.practicum.ewm.service.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.service.category.dto.CategoryDto;
import ru.practicum.ewm.service.user.dto.UserShortDto;
import ru.practicum.ewm.service.util.UtilConstants;

import java.time.LocalDateTime;

@Data
@Builder
public class EventShortDto {
    private Long id;

    private UserShortDto initiator;
    private CategoryDto category;

    private String title;
    private String annotation;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = UtilConstants.DATETIME_FORMAT)
    private LocalDateTime eventDate;

    private Boolean paid;

    private Long confirmedRequests;
    private Long views;
}
