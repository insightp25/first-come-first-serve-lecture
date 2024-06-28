package tdd.cleanarchitecture.firstcomefirstserve.infrastructure.session;

import jakarta.persistence.Embeddable;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.UserSessionId;

@Embeddable
public class UserSessionEntityId {

    private Long sessionId;
    private Long userId;

    public static UserSessionEntityId from(
        UserSessionId userSessionId
    ) {
        UserSessionEntityId userSessionEntityId =
            new UserSessionEntityId();

        userSessionEntityId.sessionId = userSessionId.sessionId();
        userSessionEntityId.userId = userSessionId.userId();

        return userSessionEntityId;
    }

    public UserSessionId toModel() {
        return UserSessionId.builder()
            .sessionId(sessionId)
            .userId(userId)
            .build();
    }
}
