package tdd.cleanarchitecture.firstcomefirstserve.session.infrastructure.lecture;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import tdd.cleanarchitecture.firstcomefirstserve.session.domain.Lecture;

@Entity
@Getter
@Table(name = "lectures")
public class LectureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    public Lecture toModel() {
        return Lecture.builder()
            .build();
    }
}
