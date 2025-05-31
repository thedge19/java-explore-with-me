package ru.practicum.ewm.service.comments.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.comments.dto.CommentDto;
import ru.practicum.ewm.service.comments.dto.CommentNewDto;
import ru.practicum.ewm.service.comments.dto.CommentUpdateRequest;
import ru.practicum.ewm.service.comments.service.CommentService;

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
    public ResponseEntity<CommentDto> patch(
            @PathVariable @Min(1) long userId,
            @PathVariable @Min(1) long commentId,
            @RequestHeader(name = "X-Request-Id", required = false)
            @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$")
            String requestId,
            @Valid @RequestBody CommentUpdateRequest commentUpdateRequest) {

        // Бизнес-логика
        CommentDto updatedComment = commentService.patchByUser(userId, commentId, commentUpdateRequest);

        HttpHeaders headers = new HttpHeaders();
        if (requestId != null) {
            headers.add("X-Request-Id", requestId);
        }

        return ResponseEntity.ok()
                .headers(headers)
                .body(updatedComment);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long userId, @PathVariable long commentId) {
        commentService.deleteByUser(userId, commentId);
    }
}
