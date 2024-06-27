package tdd.cleanarchitecture.firstcomefirstserve.infrastructure.session;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionApplicationHistoryJpaRepository extends
    JpaRepository<SessionApplicationHistoryEntity, SessionApplicationHistoryIdEntity> {

    List<SessionApplicationHistoryEntity> findByIdUserId(Long userId);
}
