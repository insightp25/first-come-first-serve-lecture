package tdd.cleanarchitecture.firstcomefirstserve.session.service.port;

import java.util.List;
import tdd.cleanarchitecture.firstcomefirstserve.session.domain.SessionApplicationHistory;

public interface SessionApplicationHistoryRepository {

    List<SessionApplicationHistory> findByUserId(Long userId);

    void save(SessionApplicationHistory sessionApplicationHistory);
}
