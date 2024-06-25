package tdd.cleanarchitecture.firstcomefirstserve.domain;

import java.time.LocalDateTime;
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
}
