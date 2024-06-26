package tdd.cleanarchitecture.firstcomefirstserve.session.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tdd.cleanarchitecture.firstcomefirstserve.session.controller.port.SessionApplicationHistoryService;
import tdd.cleanarchitecture.firstcomefirstserve.session.controller.port.SessionService;
import tdd.cleanarchitecture.firstcomefirstserve.session.domain.Session;
import tdd.cleanarchitecture.firstcomefirstserve.session.domain.SessionApplicationHistory;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessions")
public class SessionController {

    private final SessionService sessionService;

    private final SessionApplicationHistoryService sessionApplicationHistoryService;

    @PostMapping("/{session_id}/application")
    public ResponseEntity<Session> application(
        @PathVariable(value = "session_id") long lectureId,
        @RequestParam(value = "user_id") long userId
    ) {
        return ResponseEntity
            .ok()
            .body(sessionService.apply(userId, lectureId));
    }

    @GetMapping
    public ResponseEntity<List<Session>> availableSessions() {
        return ResponseEntity
            .ok()
            .body(sessionService.searchAllAvailable());
    }

    @GetMapping("/application/{user_id}")
    public ResponseEntity<List<SessionApplicationHistory>> registeredSessions(
        @PathVariable(value = "user_id") long userId
    ) {
        return ResponseEntity
            .ok()
            .body(sessionApplicationHistoryService.searchSessionApplicationHistory(userId));
    }
}
