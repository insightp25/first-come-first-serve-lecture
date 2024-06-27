package tdd.cleanarchitecture.firstcomefirstserve.session.infrastructure.session_application_history;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import tdd.cleanarchitecture.firstcomefirstserve.session.domain.SessionApplicationHistory;

@Entity
public class SessionApplicationHistoryEntity {

    @EmbeddedId
    SessionApplicationHistoryIdEntity id;

    @Column(nullable = false, name = "is_registered")
    Boolean isRegistered;

    @Column(nullable = false, name = "created_at")
    LocalDateTime createdAt;

    public static SessionApplicationHistoryEntity from(
        SessionApplicationHistory sessionApplicationHistory
    ) {
        SessionApplicationHistoryEntity sessionApplicationHistoryEntity =
            new SessionApplicationHistoryEntity();

        sessionApplicationHistoryEntity.id = SessionApplicationHistoryIdEntity
                .from(sessionApplicationHistory.sessionApplicationHistoryId());
        sessionApplicationHistoryEntity.isRegistered = sessionApplicationHistory.isRegistered();
        sessionApplicationHistoryEntity.createdAt = sessionApplicationHistory.createdAt();

        return sessionApplicationHistoryEntity;
    }

    public SessionApplicationHistory toModel() {
        return SessionApplicationHistory.builder()
            .sessionApplicationHistoryId(id.toModel())
            .isRegistered(isRegistered)
            .createdAt(createdAt)
            .build();
    }
}
