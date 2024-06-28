package tdd.cleanarchitecture.firstcomefirstserve.domain.session;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tdd.cleanarchitecture.firstcomefirstserve.controller.session.port.SessionService;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.exception.SessionNotFoundException;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.port.UserSessionRepository;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.port.SessionRepository;
import tdd.cleanarchitecture.firstcomefirstserve.domain.user.port.UserRepository;

@Service
@Transactional // 확인 필요
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final UserSessionRepository userSessionRepository;
    private final UserRepository userRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE) // 확인 필요
    public Session register(Long userId, Long sessionId) {

        Session session = sessionRepository.findById(sessionId)
            .orElseThrow(SessionNotFoundException::new);

        boolean isAvailableSession = session.validateIfAvailable();

        System.out.println("check");

        userSessionRepository.save(UserSession.builder()
            .session(session)
            .user(userRepository.findById(userId))
            .isRegistered(isAvailableSession)
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build());

        return sessionRepository.save(session.update());
    }

    public List<Session> searchAllAvailable() {
        return sessionRepository.getAll().stream()
            .filter(Session::isAvailable)
            .toList();
    }
}
