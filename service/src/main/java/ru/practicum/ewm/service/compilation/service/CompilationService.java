package ru.practicum.ewm.service.compilation.service;

import ru.practicum.ewm.service.compilation.dto.CompilationDto;
import ru.practicum.ewm.service.compilation.dto.CompilationNewDto;
import ru.practicum.ewm.service.compilation.dto.CompilationUpdateRequest;

import java.util.List;

public interface CompilationService {

    List<CompilationDto> getAll(Boolean pinned, int from, int size);

    CompilationDto getById(long compId);

    CompilationDto create(CompilationNewDto compilationNewDto);

    CompilationDto patch(long compId, CompilationUpdateRequest compilationUpdateRequest);

    void delete(long compId);
}
