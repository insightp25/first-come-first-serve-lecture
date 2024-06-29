package tdd.cleanarchitecture.firstcomefirstserve.infrastructure.session;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.UserSession;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.port.UserSessionRepository;

@Repository
@RequiredArgsConstructor
public class UserSessionRepositoryImpl implements
    UserSessionRepository {

    private final UserSessionJpaRepository userSessionJpaRepository;

    @Override
    public List<UserSession> findByUserId(Long userId) {
        return userSessionJpaRepository.findByIdUserEntityId(userId).stream()
            .map(UserSessionEntity::toModel)
            .toList();
    }

    @Override
    public void save(UserSession userSession) {
        userSessionJpaRepository.save(UserSessionEntity
            .from(userSession));
    }
}
