package tdd.cleanarchitecture.firstcomefirstserve.domain.session;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.cleanarchitecture.firstcomefirstserve.controller.session.port.UserSessionService;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.port.UserSessionRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserUserSessionServiceImpl implements UserSessionService {

    private final UserSessionRepository userSessionRepository;

    @Override
    public List<UserSession> searchSessionApplicationHistory(long userId) {
        return userSessionRepository.findByUserId(userId);
    }
}
