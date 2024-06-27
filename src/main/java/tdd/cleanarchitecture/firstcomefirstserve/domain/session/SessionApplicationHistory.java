package tdd.cleanarchitecture.firstcomefirstserve.domain.session;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.Builder;

public record SessionApplicationHistory(
    SessionApplicationHistoryId sessionApplicationHistoryId,
    boolean isRegistered,
    LocalDateTime createdAt
) {

    @Builder
    public SessionApplicationHistory{
    }

    public SessionApplicationHistory create(
        boolean isRegistered
    ) {
        return SessionApplicationHistory.builder()
            .sessionApplicationHistoryId(sessionApplicationHistoryId)
            .isRegistered(isRegistered)
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build();
    }
}
