package tdd.cleanarchitecture.firstcomefirstserve.domain.session;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.Builder;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.exception.SessionUnavailableException;

public record Session (
    long id,
    LocalDateTime startsAt,
    int numRegisteredApplicants,
    LocalDateTime createdAt,
    Lecture lecture
) {
    @Builder
    public Session {
    }

    public Session update(
    ) {
        return Session.builder()
            .id(id)
            .startsAt(startsAt)
            .numRegisteredApplicants(numRegisteredApplicants + 1)
            .createdAt(createdAt)
            .lecture(lecture)
            .build();
    }

    public boolean validateIfAvailable() {
        if (!(this.numRegisteredApplicants() < this.lecture().capacity() &&
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).isBefore(this.startsAt()))
        ) {
            throw new SessionUnavailableException();
        }
        return true;
    }

    public boolean isAvailable() {
        return this.numRegisteredApplicants() < this.lecture().capacity() &&
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).isBefore(this.startsAt());
    }
}
