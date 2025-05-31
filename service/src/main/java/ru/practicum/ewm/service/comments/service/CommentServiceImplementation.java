package ru.practicum.ewm.service.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.service.comments.dto.CommentDto;
import ru.practicum.ewm.service.comments.dto.CommentMapper;
import ru.practicum.ewm.service.comments.dto.CommentNewDto;
import ru.practicum.ewm.service.comments.dto.CommentUpdateRequest;
import ru.practicum.ewm.service.comments.model.Comment;
import ru.practicum.ewm.service.comments.repository.CommentRepository;
import ru.practicum.ewm.service.event.model.Event;
import ru.practicum.ewm.service.event.repository.EventRepository;
import ru.practicum.ewm.service.user.model.User;
import ru.practicum.ewm.service.user.repository.UserRepository;
import ru.practicum.ewm.service.util.exception.ForbiddenException;
import ru.practicum.ewm.service.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImplementation implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Transactional(readOnly = true)
    public Page<CommentDto> getAllByEventId(long eventId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentRepository.findAllByEventId(eventId, pageable)
                .map(CommentMapper.INSTANCE::toDto);
    }

    @Transactional
    public CommentDto create(long userId, long eventId, CommentNewDto dto) {
        User user = findUserById(userId);
        Event event = findEventById(eventId);

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setEvent(event);
        comment.setText(dto.getText());
        comment.setCreatedOn(LocalDateTime.now());

        comment = commentRepository.save(comment);

        return CommentMapper.INSTANCE.toDto(comment);
    }

    @Transactional
    public CommentDto patchByUser(long userId, long commentId, CommentUpdateRequest updateRequest) {
        findUserById(userId);
        Comment comment = findCommentById(commentId);

        if (!comment.getUser().getId().equals(userId)) {
            throw new ForbiddenException("User id=" + userId + " not owner of Comment id=" + commentId);
        }

        Optional.ofNullable(updateRequest.getText()).ifPresent(comment::setText);

        return CommentMapper.INSTANCE.toDto(comment);
    }

    @Transactional
    public CommentDto patchByAdmin(long commentId, CommentUpdateRequest updateRequest) {
        Comment comment = findCommentById(commentId);

        Optional.ofNullable(updateRequest.getText()).ifPresent(comment::setText);

        return CommentMapper.INSTANCE.toDto(comment);
    }

    @Transactional
    public void deleteByUser(long userId, long commentId) {
        findUserById(userId);
        Comment comment = findCommentById(commentId);

        if (!comment.getUser().getId().equals(userId)) {
            throw new ForbiddenException("User id=" + userId + " not owner of Comment id=" + commentId);
        }

        commentRepository.deleteById(commentId);
    }

    @Transactional
    public void deleteByAdmin(long commentId) {
        commentRepository.deleteById(commentId);
    }

    private Comment findCommentById(long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment with id=" + id + " was not found"));
    }

    private User findUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id=" + id + " was not found"));
    }

    private Event findEventById(long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id=" + id + " was not found"));
    }
}
