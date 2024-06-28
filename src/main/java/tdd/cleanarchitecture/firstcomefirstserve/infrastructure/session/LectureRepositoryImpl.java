package tdd.cleanarchitecture.firstcomefirstserve.infrastructure.session;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.port.LectureRepository;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public LectureEntity save(LectureEntity lectureEntity) {
        return lectureJpaRepository.save(lectureEntity);
    }
}
