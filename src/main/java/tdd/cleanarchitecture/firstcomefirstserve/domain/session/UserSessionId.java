package tdd.cleanarchitecture.firstcomefirstserve.domain.session;

import lombok.Builder;

public record UserSessionId(
    Long userId,
    Long sessionId
) {

    @Builder
    public UserSessionId {
    }
}
