package tdd.cleanarchitecture.firstcomefirstserve.session.infrastructure.session;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import tdd.cleanarchitecture.firstcomefirstserve.session.domain.Session;

@Entity
public class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String hostName;

    String title;

    String content;

    Integer capacity;

    LocalDateTime held_at;

    Integer numRegisteredApplicants;

    Boolean isClosed;

    LocalDateTime createdAt;

    public static SessionEntity from(Session session) {
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.id = session.id();
        sessionEntity.hostName = session.hostName();
        sessionEntity.title = session.title();
        sessionEntity.content = session.content();
        sessionEntity.capacity = session.capacity();
        sessionEntity.held_at = session.heldAt();
        sessionEntity.numRegisteredApplicants = session.numRegisteredApplicants();
        sessionEntity.isClosed = session.isClosed();
        sessionEntity.createdAt = session.createdAt();
        return sessionEntity;
    }

    public Session toModel() {
        return Session.builder()
            .build();
    }
}
