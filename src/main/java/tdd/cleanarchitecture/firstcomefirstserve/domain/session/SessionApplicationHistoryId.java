package tdd.cleanarchitecture.firstcomefirstserve.domain.session;

import lombok.Builder;

public record SessionApplicationHistoryId(
    Long userId,
    Long sessionId
) {

    @Builder
    public SessionApplicationHistoryId {
    }
}
