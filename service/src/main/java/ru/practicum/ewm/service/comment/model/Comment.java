package ru.practicum.ewm.service.comment.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.service.event.model.Event;
import ru.practicum.ewm.service.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_user_owner_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_event_id")
    private Event event;

    @Column(name = "comment_text")
    private String text;

    @Column(name = "comment_timestamp")
    private LocalDateTime createdOn;
}
