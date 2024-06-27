package tdd.cleanarchitecture.firstcomefirstserve.user.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.Builder;

public record User(
    Long id,
    LocalDateTime createdAt
) {
    @Builder
    public User {
    }

    public static User from(
    ) {
        return User.builder()
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build();
    }
}
