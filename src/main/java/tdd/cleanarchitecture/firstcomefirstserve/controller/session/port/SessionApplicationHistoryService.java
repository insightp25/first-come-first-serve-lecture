package tdd.cleanarchitecture.firstcomefirstserve.controller.session.port;

import java.util.List;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.SessionApplicationHistory;

public interface SessionApplicationHistoryService {
    public List<SessionApplicationHistory> searchSessionApplicationHistory(long userId);
}
