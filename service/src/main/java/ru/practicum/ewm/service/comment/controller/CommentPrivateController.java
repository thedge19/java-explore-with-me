package ru.practicum.ewm.service.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.comment.dto.CommentDto;
import ru.practicum.ewm.service.comment.dto.CommentNewDto;
import ru.practicum.ewm.service.comment.dto.CommentUpdateRequest;
import ru.practicum.ewm.service.comment.service.CommentService;

@RestController
@RequestMapping(path = "/users/{userId}/comments")
@RequiredArgsConstructor
public class CommentPrivateController {
    private final CommentService commentService;

    @PostMapping("/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create(@PathVariable long userId,
                             @PathVariable long eventId,
                             @Valid @RequestBody CommentNewDto commentNewDto) {
        return commentService.create(userId, eventId, commentNewDto);
    }

    @PatchMapping("/{commentId}")
    public CommentDto patch(@PathVariable long userId,
                            @PathVariable long commentId,
                            @Valid @RequestBody CommentUpdateRequest commentUpdateRequest) {
        return commentService.patchByUser(userId, commentId, commentUpdateRequest);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long userId, @PathVariable long commentId) {
        commentService.deleteByUser(userId, commentId);
    }
}
