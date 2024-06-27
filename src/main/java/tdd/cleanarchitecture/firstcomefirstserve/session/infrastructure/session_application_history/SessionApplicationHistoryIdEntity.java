package tdd.cleanarchitecture.firstcomefirstserve.session.infrastructure.session_application_history;

import jakarta.persistence.Embeddable;
import tdd.cleanarchitecture.firstcomefirstserve.session.domain.SessionApplicationHistoryId;

@Embeddable
public class SessionApplicationHistoryIdEntity {

    Long sessionId;

    Long userId;

    public static SessionApplicationHistoryIdEntity from(
        SessionApplicationHistoryId sessionApplicationHistoryId
    ) {
        SessionApplicationHistoryIdEntity sessionApplicationHistoryIdEntity =
            new SessionApplicationHistoryIdEntity();

        sessionApplicationHistoryIdEntity.sessionId = sessionApplicationHistoryId.sessionId();
        sessionApplicationHistoryIdEntity.userId = sessionApplicationHistoryId.userId();

        return sessionApplicationHistoryIdEntity;
    }

    public SessionApplicationHistoryId toModel() {
        return SessionApplicationHistoryId.builder()
            .sessionId(sessionId)
            .userId(userId)
            .build();
    }
}
