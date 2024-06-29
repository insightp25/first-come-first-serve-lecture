package tdd.cleanarchitecture.firstcomefirstserve.domain.session;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.cleanarchitecture.firstcomefirstserve.controller.session.port.SessionService;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.exception.SessionNotFoundException;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.port.SessionRepository;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.port.UserSessionRepository;
import tdd.cleanarchitecture.firstcomefirstserve.domain.user.port.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final UserSessionRepository userSessionRepository;
    private final UserRepository userRepository;

    @Override
    public Session register(Long userId, Long sessionId) {

        Session session = sessionRepository.findByIdLocked(sessionId)
            .orElseThrow(SessionNotFoundException::new);

        boolean isAvailableSession = session.validateIfAvailable();

        userSessionRepository.save(UserSession.builder()
            .session(session)
            .user(userRepository.findById(userId))
            .isRegistered(isAvailableSession)
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build());

        return sessionRepository.save(session.update());
    }

    @Override
    public List<Session> searchAllAvailable() {
        return sessionRepository.getAll().stream()
            .filter(Session::isAvailable)
            .toList();
    }

    /**
     * 동시성 테스트를 위한 테스트 전용 더미 메서드입니다.
     */
    @Override
    @Deprecated
    public Session findByIdLocked_ForTestPurposeOnly(Long sessionId) {
        return sessionRepository.findByIdLocked(sessionId)
            .orElseThrow(SessionNotFoundException::new);
    }
}
