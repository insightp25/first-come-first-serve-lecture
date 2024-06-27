package tdd.cleanarchitecture.firstcomefirstserve.session.service.port;

import java.util.Optional;
import tdd.cleanarchitecture.firstcomefirstserve.session.domain.Lecture;

public interface LectureRepository {

    Optional<Lecture> findById(Long id);
}
