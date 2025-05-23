package ru.practicum.ewm.service.compilation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.compilation.dto.CompilationDto;
import ru.practicum.ewm.service.compilation.dto.CompilationNewDto;
import ru.practicum.ewm.service.compilation.dto.CompilationUpdateRequest;
import ru.practicum.ewm.service.compilation.service.CompilationService;

@Validated
@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class CompilationControllerAdmin {
    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto create(@Valid @RequestBody CompilationNewDto compilationNewDto) {
        return compilationService.create(compilationNewDto);
    }

    @PatchMapping("/{compId}")
    public CompilationDto patch(@PathVariable long compId,
                                @Valid @RequestBody CompilationUpdateRequest compilationUpdateRequest) {
        return compilationService.patch(compId, compilationUpdateRequest);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long compId) {
        compilationService.delete(compId);
    }
}
