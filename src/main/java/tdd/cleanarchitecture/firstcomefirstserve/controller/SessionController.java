package tdd.cleanarchitecture.firstcomefirstserve.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tdd.cleanarchitecture.firstcomefirstserve.controller.port.SessionService;
import tdd.cleanarchitecture.firstcomefirstserve.domain.Session;
import tdd.cleanarchitecture.firstcomefirstserve.domain.SessionApplicationHistory;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessions")
public class SessionController {

    private final SessionService sessionService;

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
            .body(sessionService.searchAvailable());
    }

    @GetMapping("/application/{userId}")
    public ResponseEntity<List<SessionApplicationHistory>> registeredSessions() {
        return ResponseEntity
            .ok()
            .body(sessionService.searchApplicationHistory());
    }
}

