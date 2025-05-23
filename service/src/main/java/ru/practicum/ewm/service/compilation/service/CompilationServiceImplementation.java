package ru.practicum.ewm.service.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.service.compilation.dto.CompilationDto;
import ru.practicum.ewm.service.compilation.dto.CompilationMapper;
import ru.practicum.ewm.service.compilation.dto.CompilationNewDto;
import ru.practicum.ewm.service.compilation.dto.CompilationUpdateRequest;
import ru.practicum.ewm.service.compilation.model.Compilation;
import ru.practicum.ewm.service.compilation.repository.CompilationRepository;
import ru.practicum.ewm.service.event.model.Event;
import ru.practicum.ewm.service.event.repository.EventRepository;
import ru.practicum.ewm.service.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CompilationServiceImplementation implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;


    public List<CompilationDto> getAll(Boolean pinned, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);

        return compilationRepository.findAllByPublic(pinned, pageable).stream()
                .map(CompilationMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public CompilationDto getById(long compId) {
        return CompilationMapper.INSTANCE.toDto(findCompilationById(compId));
    }

    @Transactional
    public CompilationDto create(CompilationNewDto compilationNewDto) {
        List<Event> events = compilationNewDto.getEvents() != null && !compilationNewDto.getEvents().isEmpty() ?
                eventRepository.findAllById(compilationNewDto.getEvents()) :
                new ArrayList<>();

        if (compilationNewDto.getPinned() == null) {
            compilationNewDto.setPinned(false);
        }

        return CompilationMapper.INSTANCE.toDto(compilationRepository.save(CompilationMapper.INSTANCE.fromDto(compilationNewDto, events)));
    }

    @Transactional
    public CompilationDto patch(long compId, CompilationUpdateRequest compilationUpdateRequest) {
        Compilation compilation = findCompilationById(compId);

        if (compilationUpdateRequest.getEvents() != null) {
            compilation.setEvents(eventRepository.findAllById(compilationUpdateRequest.getEvents()));
        }

        Optional.ofNullable(compilationUpdateRequest.getTitle()).ifPresent(compilation::setTitle);
        Optional.ofNullable(compilationUpdateRequest.getPinned()).ifPresent(compilation::setPinned);

        return CompilationMapper.INSTANCE.toDto(compilation);
    }

    @Transactional
    public void delete(long compId) {
        findCompilationById(compId);
        compilationRepository.deleteById(compId);
    }

    private Compilation findCompilationById(long id) {
        return compilationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Compilation with id=" + id + " was not found"));
    }
}
