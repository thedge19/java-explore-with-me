package ru.practicum.ewm.service.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.service.event.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByLatAndLon(float lat, float lon);
}
