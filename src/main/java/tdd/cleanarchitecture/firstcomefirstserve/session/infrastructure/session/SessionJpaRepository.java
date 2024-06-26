package tdd.cleanarchitecture.firstcomefirstserve.session.infrastructure.session;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionJpaRepository extends JpaRepository<SessionEntity, Long> {
}
