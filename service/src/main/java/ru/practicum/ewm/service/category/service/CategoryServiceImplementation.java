package ru.practicum.ewm.service.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.service.category.model.Category;
import ru.practicum.ewm.service.category.dto.CategoryDto;
import ru.practicum.ewm.service.category.dto.CategoryMapper;
import ru.practicum.ewm.service.category.repository.CategoryRepository;
import ru.practicum.ewm.service.event.repository.EventRepository;
import ru.practicum.ewm.service.util.exception.ConflictException;
import ru.practicum.ewm.service.util.exception.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CategoryServiceImplementation implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    public List<CategoryDto> getAll(int from, int size) {
        return categoryRepository.findAll(PageRequest.of(from, size)).getContent().stream()
                .map(CategoryMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public CategoryDto getById(long catId) {
        return CategoryMapper.INSTANCE.toDto(findById(catId));
    }

    @Transactional
    public CategoryDto create(CategoryDto categoryDto) {
        return CategoryMapper.INSTANCE.toDto(categoryRepository.save(CategoryMapper.INSTANCE.fromDto(categoryDto)));
    }

    @Transactional
    public CategoryDto patch(long catId, CategoryDto categoryDto) {
        Category stored = findById(catId);

        Optional.ofNullable(categoryDto.getName()).ifPresent(stored::setName);

        return CategoryMapper.INSTANCE.toDto(categoryRepository.save(stored));
    }

    @Transactional
    public void delete(long catId) {
        findById(catId);

        if (eventRepository.existsByCategoryId(catId)) {
            throw new ConflictException("Cannot delete category with id=" + catId + " because it has associated events");
        }

        categoryRepository.deleteById(catId);
    }

    private Category findById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id=" + id + " was not found"));
    }
}
