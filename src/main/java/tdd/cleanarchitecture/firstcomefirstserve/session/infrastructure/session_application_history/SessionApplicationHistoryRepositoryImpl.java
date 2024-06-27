package tdd.cleanarchitecture.firstcomefirstserve.session.infrastructure.session_application_history;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tdd.cleanarchitecture.firstcomefirstserve.session.domain.SessionApplicationHistory;
import tdd.cleanarchitecture.firstcomefirstserve.session.service.port.SessionApplicationHistoryRepository;

@Repository
@RequiredArgsConstructor
public class SessionApplicationHistoryRepositoryImpl implements
    SessionApplicationHistoryRepository {

    private final SessionApplicationHistoryJpaRepository sessionApplicationHistoryJpaRepository;

    @Override
    public List<SessionApplicationHistory> findByUserId(Long userId) {
        return sessionApplicationHistoryJpaRepository.findByIdUserId(userId).stream()
            .map(SessionApplicationHistoryEntity::toModel)
            .toList();
    }

    @Override
    public void save(SessionApplicationHistory sessionApplicationHistory) {
        sessionApplicationHistoryJpaRepository.save(SessionApplicationHistoryEntity
            .from(sessionApplicationHistory));
    }
}
