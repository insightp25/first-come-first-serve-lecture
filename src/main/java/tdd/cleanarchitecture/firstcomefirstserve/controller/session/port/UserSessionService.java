package tdd.cleanarchitecture.firstcomefirstserve.controller.session.port;

import java.util.List;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.UserSession;

public interface UserSessionService {
    public List<UserSession> searchSessionApplicationHistory(long userId);
}
