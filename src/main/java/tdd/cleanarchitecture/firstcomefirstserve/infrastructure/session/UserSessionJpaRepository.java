package tdd.cleanarchitecture.firstcomefirstserve.infrastructure.session;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserSessionJpaRepository extends JpaRepository<UserSessionEntity, Long> {

    @Query("SELECT u FROM UserSessionEntity u WHERE u.userEntity.id = :userId")
    List<UserSessionEntity> findByIdUserEntityId(@Param("userId") Long userId);
}
