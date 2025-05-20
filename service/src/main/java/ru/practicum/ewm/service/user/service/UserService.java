package ru.practicum.ewm.service.user.service;

import ru.practicum.ewm.service.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> get(List<Long> ids, int from, int size);

    UserDto create(UserDto userDto);

    void delete(long userId);
}
