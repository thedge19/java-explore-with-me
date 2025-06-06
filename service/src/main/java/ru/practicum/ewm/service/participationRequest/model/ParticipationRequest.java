package ru.practicum.ewm.service.participationRequest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.service.event.model.Event;
import ru.practicum.ewm.service.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "participation_requests")
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_request_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "participation_request_user_id")
    private User requester;

    @ManyToOne
    @JoinColumn(name = "participation_request_event_id")
    private Event event;

    @Enumerated(EnumType.STRING)
    @Column(name = "participation_request_status")
    private ParticipationRequestState status;

    @Column(name = "participation_request_created_date")
    private LocalDateTime created;
}
