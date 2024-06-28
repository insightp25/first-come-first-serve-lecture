package tdd.cleanarchitecture.firstcomefirstserve.infrastructure.session;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionJpaRepository extends
    JpaRepository<UserSessionEntity, UserSessionEntityId> {

    List<UserSessionEntity> findByIdUserId(Long userId);
}
