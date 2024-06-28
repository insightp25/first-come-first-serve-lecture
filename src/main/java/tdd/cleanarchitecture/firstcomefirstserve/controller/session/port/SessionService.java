package tdd.cleanarchitecture.firstcomefirstserve.controller.session.port;

import java.util.List;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.Session;

public interface SessionService {

    public Session register(Long userId, Long sessionId);

    public List<Session> searchAllAvailable();
}
