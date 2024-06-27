package tdd.cleanarchitecture.firstcomefirstserve.session.domain;

import lombok.Builder;

public record Lecture(
    Long id
) {

    @Builder
    public Lecture {
    }
}
