package tdd.cleanarchitecture.firstcomefirstserve.infrastructure.session;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureJpaRepository extends JpaRepository<LectureEntity, Long> {
}
