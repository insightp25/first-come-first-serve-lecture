package tdd.cleanarchitecture.firstcomefirstserve.domain.session.port;

import tdd.cleanarchitecture.firstcomefirstserve.infrastructure.session.LectureEntity;

public interface LectureRepository {

    LectureEntity save(LectureEntity lectureEntity);
}
