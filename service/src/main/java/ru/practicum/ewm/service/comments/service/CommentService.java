package ru.practicum.ewm.service.comments.service;

import ru.practicum.ewm.service.comments.dto.CommentDto;
import ru.practicum.ewm.service.comments.dto.CommentNewDto;
import ru.practicum.ewm.service.comments.dto.CommentUpdateRequest;

import java.util.List;

public interface CommentService {
    List<CommentDto> getAllByEventId(long eventId);

    CommentDto create(long userId, long eventId, CommentNewDto dto);

    CommentDto patchByUser(long userId, long commentId, CommentUpdateRequest updateRequest);

    CommentDto patchByAdmin(long commentId, CommentUpdateRequest updateRequest);

    void deleteByUser(long userId, long commentId);

    void deleteByAdmin(long commentId);
}
