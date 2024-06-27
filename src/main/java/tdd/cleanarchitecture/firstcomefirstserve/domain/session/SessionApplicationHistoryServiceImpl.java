package tdd.cleanarchitecture.firstcomefirstserve.domain.session;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.cleanarchitecture.firstcomefirstserve.controller.session.port.SessionApplicationHistoryService;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.port.SessionApplicationHistoryRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class SessionApplicationHistoryServiceImpl implements SessionApplicationHistoryService {

    private final SessionApplicationHistoryRepository sessionApplicationHistoryRepository;

    @Override
    public List<SessionApplicationHistory> searchSessionApplicationHistory(long userId) {
        return sessionApplicationHistoryRepository.findByUserId(userId);
    }
}
