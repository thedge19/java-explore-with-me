package ru.practicum.ewm.service.category.service;

import ru.practicum.ewm.service.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAll(int from, int size);

    CategoryDto getById(long catId);

    CategoryDto create(CategoryDto categoryDto);

    CategoryDto patch(long catId, CategoryDto categoryDto);

    void delete(long catId);
}
