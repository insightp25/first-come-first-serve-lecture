package tdd.cleanarchitecture.firstcomefirstserve.session.controller.port;

import java.util.List;
import tdd.cleanarchitecture.firstcomefirstserve.session.domain.SessionApplicationHistory;

public interface SessionApplicationHistoryService {
    public List<SessionApplicationHistory> searchSessionApplicationHistory(long userId);
}
