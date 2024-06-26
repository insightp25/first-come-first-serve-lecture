package tdd.cleanarchitecture.firstcomefirstserve.session.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tdd.cleanarchitecture.firstcomefirstserve.session.controller.port.SessionService;
import tdd.cleanarchitecture.firstcomefirstserve.session.domain.Lecture;
import tdd.cleanarchitecture.firstcomefirstserve.session.domain.Session;
import tdd.cleanarchitecture.firstcomefirstserve.session.domain.SessionApplicationHistory;
import tdd.cleanarchitecture.firstcomefirstserve.common.domain.exception.SessionUnavailableException;
import tdd.cleanarchitecture.firstcomefirstserve.session.service.port.LectureRepository;
import tdd.cleanarchitecture.firstcomefirstserve.session.service.port.SessionApplicationHistoryRepository;
import tdd.cleanarchitecture.firstcomefirstserve.session.service.port.SessionRepository;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final SessionApplicationHistoryRepository sessionApplicationHistoryRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Session apply(Long userId, Long sessionId) {
        // session 이 없을 때 예외 던지는 건 중요하다. 왜냐하면 아닌 사람이 session 을 생성할 수 있게 되기 때문에
        Session session = sessionRepository.findById(sessionId).orElseThrow(); // 세션 정보를 찾는다

        if (session.numRegisteredApplicants() < session.capacity() && // 정원이 잔여석이 있거나
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).isBefore(session.heldAt())) { // 신청기간이 만료되지 않은 경우
            createSessionApplicationHistory(userId, sessionId, true);
            return sessionRepository.save(session.update());
        } else {
            createSessionApplicationHistory(userId, sessionId, false);
            throw new SessionUnavailableException();
        }
    }

    public List<Session> searchAllAvailable() {
        return sessionRepository.getAll().stream()
            .filter(session -> LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).isBefore(session.heldAt()))
            .filter(session -> session.numRegisteredApplicants() < session.capacity())
            .toList();
    }

    private void createSessionApplicationHistory(Long userId, Long sessionId, boolean isRegistered) {
        sessionApplicationHistoryRepository.save(SessionApplicationHistory.builder()
            .sessionId(sessionId)
            .userId(userId)
            .isRegistered(isRegistered)
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build());
    }
}
