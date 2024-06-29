package tdd.cleanarchitecture.firstcomefirstserve.domain.session;

import java.time.LocalDateTime;
import lombok.Builder;

public record Lecture(
    long id,
    String hostName,
    String title,
    String content,
    int capacity,
    LocalDateTime createdAt
) {
    @Builder
    public Lecture {
    }
}
