package ru.practicum.ewm.service.event.data.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.service.event.data.location.LocationDto;
import ru.practicum.ewm.service.util.UtilConstants;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventUpdateAdminRequest {
    private Long category;
    private LocationDto location;

    @Size(min = 3, max = 120)
    private String title;

    @Size(min = 20, max = 2000)
    private String annotation;

    @Size(min = 20, max = 7000)
    private String description;

    @Future
    @JsonProperty("eventDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = UtilConstants.DATETIME_FORMAT)
    private LocalDateTime eventTimestamp;

    private Integer participantLimit;
    private Boolean paid;
    private Boolean requestModeration;
    private StateAction stateAction;

    public enum StateAction {
        PUBLISH_EVENT,
        REJECT_EVENT
    }
}
