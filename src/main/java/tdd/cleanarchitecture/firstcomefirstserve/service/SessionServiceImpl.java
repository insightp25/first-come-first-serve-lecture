package tdd.cleanarchitecture.firstcomefirstserve.service;

import java.util.List;
import org.springframework.stereotype.Service;
import tdd.cleanarchitecture.firstcomefirstserve.controller.port.SessionService;
import tdd.cleanarchitecture.firstcomefirstserve.domain.Session;
import tdd.cleanarchitecture.firstcomefirstserve.domain.SessionApplicationHistory;

@Service
public class SessionServiceImpl implements SessionService {

    public Session apply(Long userId, Long lectureId) {
        return null;
    }

    public List<Session> searchAvailable() {
        return null;
    }

    public List<SessionApplicationHistory> searchApplicationHistory() {
        return null;
    }
}
