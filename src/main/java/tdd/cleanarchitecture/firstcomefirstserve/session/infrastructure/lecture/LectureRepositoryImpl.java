package tdd.cleanarchitecture.firstcomefirstserve.session.infrastructure.lecture;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tdd.cleanarchitecture.firstcomefirstserve.session.domain.Lecture;
import tdd.cleanarchitecture.firstcomefirstserve.session.service.port.LectureRepository;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public Optional<Lecture> findById(Long id) {
        return lectureJpaRepository.findById(id).map(LectureEntity::toModel);
    }
}
