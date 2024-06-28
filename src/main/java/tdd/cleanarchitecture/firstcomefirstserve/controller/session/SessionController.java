package tdd.cleanarchitecture.firstcomefirstserve.controller.session;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tdd.cleanarchitecture.firstcomefirstserve.controller.session.port.UserSessionService;
import tdd.cleanarchitecture.firstcomefirstserve.controller.session.port.SessionService;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.Session;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.UserSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessions")
public class SessionController {

    private final SessionService sessionService;

    private final UserSessionService userSessionService;

    @PostMapping("/{session_id}/application")
    public ResponseEntity<Session> application(
        @PathVariable(value = "session_id") long lectureId,
        @RequestParam(value = "user_id") long userId
    ) {
        return ResponseEntity
            .ok()
            .body(sessionService.register(userId, lectureId));
    }

    @GetMapping
    public ResponseEntity<List<Session>> availableSessions() {
        return ResponseEntity
            .ok()
            .body(sessionService.searchAllAvailable());
    }

    @GetMapping("/application/{user_id}")
    public ResponseEntity<List<UserSession>> registeredSessions(
        @PathVariable(value = "user_id") long userId
    ) {
        return ResponseEntity
            .ok()
            .body(userSessionService.searchSessionApplicationHistory(userId));
    }
}
