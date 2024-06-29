package tdd.cleanarchitecture.firstcomefirstserve.infrastructure.session;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SessionJpaRepository extends JpaRepository<SessionEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM SessionEntity s WHERE s.id = :sessionId")
    Optional<SessionEntity> findByIdLocked(@Param("sessionId") Long sessionId);
}
