package tdd.cleanarchitecture.firstcomefirstserve.session.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.cleanarchitecture.firstcomefirstserve.session.controller.port.SessionApplicationHistoryService;
import tdd.cleanarchitecture.firstcomefirstserve.session.domain.SessionApplicationHistory;
import tdd.cleanarchitecture.firstcomefirstserve.session.service.port.SessionApplicationHistoryRepository;

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
