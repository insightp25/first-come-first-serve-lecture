package tdd.cleanarchitecture.firstcomefirstserve.domain.session;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.Builder;
import tdd.cleanarchitecture.firstcomefirstserve.domain.user.User;

public record UserSession(
    User user,
    Session session,
    boolean isRegistered,
    LocalDateTime createdAt
) {

    @Builder
    public UserSession {
    }

    public UserSession create(
        boolean isRegistered
    ) {
        return UserSession.builder()
            .user(user)
            .session(session)
            .isRegistered(isRegistered)
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build();
    }
}
