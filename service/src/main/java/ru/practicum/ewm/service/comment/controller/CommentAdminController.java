package ru.practicum.ewm.service.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.comment.dto.CommentDto;
import ru.practicum.ewm.service.comment.dto.CommentUpdateRequest;
import ru.practicum.ewm.service.comment.service.CommentService;

@RestController
@RequestMapping(path = "/admin/comments")
@RequiredArgsConstructor
public class CommentAdminController {
    private final CommentService commentService;

    @PatchMapping("/{commentId}")
    public CommentDto patch(@PathVariable long commentId,
                            @Valid @RequestBody CommentUpdateRequest commentUpdateRequest) {
        return commentService.patchByAdmin(commentId, commentUpdateRequest);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long commentId) {
        commentService.deleteByAdmin(commentId);
    }
}
