package tdd.cleanarchitecture.firstcomefirstserve.domain.session.port;

import java.util.List;
import java.util.Optional;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.Session;

public interface SessionRepository {

    Optional<Session> findByIdLocked(Long sessionId);

    Session save(Session session);

    List<Session> getAll();
}
