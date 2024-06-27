package tdd.cleanarchitecture.firstcomefirstserve.session.controller.port;

import java.util.List;
import tdd.cleanarchitecture.firstcomefirstserve.session.domain.Session;

public interface SessionService {

    public Session apply(Long userId, Long lectureId);

    public List<Session> searchAllAvailable();
}
