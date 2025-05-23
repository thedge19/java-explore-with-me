package ru.practicum.ewm.service.participationRequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.service.participationRequest.model.ParticipationRequestState;
import ru.practicum.ewm.service.participationRequest.model.ParticipationRequest;

import java.util.List;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {
    List<ParticipationRequest> findAllByRequesterId(Long id);

    List<ParticipationRequest> findAllByEventId(Long id);

    List<ParticipationRequest> findAllByIdIn(List<Long> ids);

    @Query("SELECT COUNT(pr) FROM ParticipationRequest pr WHERE pr.event.id = :eventId AND pr.status = :status")
    Long countByEventIdAndStatus(@Param("eventId") Long eventId,
                                 @Param("status") ParticipationRequestState status);

    @Query("SELECT pr.event.id, COUNT(pr) " +
            "FROM ParticipationRequest pr " +
            "WHERE pr.event.id IN :eventIds AND pr.status = :status " +
            "GROUP BY pr.event.id")
    List<Object[]> countByEventIdInAndStatus(
            @Param("eventIds") List<Long> eventIds,
            @Param("status") ParticipationRequestState status
    );
}
