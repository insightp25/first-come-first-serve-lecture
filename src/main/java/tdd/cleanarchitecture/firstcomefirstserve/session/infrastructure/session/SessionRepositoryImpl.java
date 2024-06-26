package tdd.cleanarchitecture.firstcomefirstserve.session.infrastructure.session;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tdd.cleanarchitecture.firstcomefirstserve.session.domain.Session;
import tdd.cleanarchitecture.firstcomefirstserve.session.service.port.SessionRepository;

@Repository
@RequiredArgsConstructor
public class SessionRepositoryImpl implements SessionRepository {

    private final SessionJpaRepository sessionJpaRepository;

    @Override
    public Optional<Session> findById(Long sessionId) {
        return sessionJpaRepository.findById(sessionId).map(SessionEntity::toModel);
    }

    @Override
    public Session save(Session session) {
        return sessionJpaRepository.save(SessionEntity.from(session)).toModel();
    }

    @Override
    public List<Session> getAll() {
        return sessionJpaRepository.findAll().stream().map(SessionEntity::toModel).toList();
    }
}
