package tdd.cleanarchitecture.firstcomefirstserve.session.domain;

import lombok.Builder;

public record SessionApplicationHistoryId(
    Long userId,
    Long sessionId
) {

    @Builder
    public SessionApplicationHistoryId {
    }
}
