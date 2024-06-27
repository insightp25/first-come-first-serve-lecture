package tdd.cleanarchitecture.firstcomefirstserve.session.service.port;

import java.util.List;
import java.util.Optional;
import tdd.cleanarchitecture.firstcomefirstserve.session.domain.Session;

public interface SessionRepository {

    Optional<Session> findById(Long sessionId);

    Session save(Session session);

    List<Session> getAll();
}
