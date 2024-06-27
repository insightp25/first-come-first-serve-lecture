package tdd.cleanarchitecture.firstcomefirstserve.domain.session.port;

import java.util.List;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.SessionApplicationHistory;

public interface SessionApplicationHistoryRepository {

    List<SessionApplicationHistory> findByUserId(Long userId);

    void save(SessionApplicationHistory sessionApplicationHistory);
}
