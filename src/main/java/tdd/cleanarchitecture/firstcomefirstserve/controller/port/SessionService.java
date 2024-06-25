package tdd.cleanarchitecture.firstcomefirstserve.controller.port;

import java.util.List;
import tdd.cleanarchitecture.firstcomefirstserve.domain.Session;
import tdd.cleanarchitecture.firstcomefirstserve.domain.SessionApplicationHistory;

public interface SessionService {

    public Session apply(Long userId, Long lectureId);

    public List<Session> searchAvailable();

    public List<SessionApplicationHistory> searchApplicationHistory();
}
