package tdd.cleanarchitecture.firstcomefirstserve.session.domain;

import java.time.LocalDateTime;
import lombok.Builder;

public record Session (
    long id,
    String hostName,
    String title,
    String content,
    int capacity,
    LocalDateTime heldAt,
    int numRegisteredApplicants,
    boolean isClosed,
    LocalDateTime createdAt
) {

    @Builder
    public Session {
    }

    public Session update(
    ) {
        return Session.builder()
            .id(id)
            .hostName(hostName)
            .title(title)
            .content(content)
            .capacity(capacity) // 분리
            .heldAt(heldAt) // 분리
            .numRegisteredApplicants(numRegisteredApplicants + 1) // 분리
            .isClosed(isClosed)
            .createdAt(createdAt)
            .build();
    }
}
