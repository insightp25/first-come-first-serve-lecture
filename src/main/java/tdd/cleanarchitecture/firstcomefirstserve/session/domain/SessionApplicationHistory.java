package tdd.cleanarchitecture.firstcomefirstserve.session.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.Builder;

public record SessionApplicationHistory(
    long sessionId,
    long userId,
    boolean isRegistered,
    LocalDateTime createdAt
) {

    @Builder
    public SessionApplicationHistory{
    }

    public SessionApplicationHistory create(boolean isRegistered) {
        return SessionApplicationHistory.builder()
            .sessionId(sessionId)
            .userId(userId)
            .isRegistered(isRegistered)
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build();
    }
}
