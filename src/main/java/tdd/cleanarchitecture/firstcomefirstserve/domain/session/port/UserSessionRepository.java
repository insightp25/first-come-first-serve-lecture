package tdd.cleanarchitecture.firstcomefirstserve.domain.session.port;

import java.util.List;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.UserSession;

public interface UserSessionRepository {

    List<UserSession> findByUserId(Long userId);

    void save(UserSession userSession);
}
