package tdd.cleanarchitecture.firstcomefirstserve.domain;

import java.time.LocalDateTime;
import lombok.Builder;

public record Session (
    long id,
    long lectureId,
    long hostId,
    LocalDateTime heldAt,
    short num_registered_applicants,
    boolean isClosed,
    LocalDateTime createdAt
) {

    @Builder
    public Session {
    }
}
