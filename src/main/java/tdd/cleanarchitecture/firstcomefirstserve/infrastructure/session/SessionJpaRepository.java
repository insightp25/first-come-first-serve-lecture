package tdd.cleanarchitecture.firstcomefirstserve.infrastructure.session;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.Session;

public interface SessionJpaRepository extends JpaRepository<SessionEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Session> findByIdLocked(Long sessionId);
}
