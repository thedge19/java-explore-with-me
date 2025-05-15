package ru.practicum.ewm.service.event.data.event;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestStatusUpdateRequest {
    @NotNull
    private List<Long> requestIds;

    @NotNull
    private EventRequestStatusUpdateRequest.StateAction status;

    public enum StateAction {
        CONFIRMED,
        REJECTED
    }
}
