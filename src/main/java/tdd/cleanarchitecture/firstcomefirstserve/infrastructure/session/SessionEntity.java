package tdd.cleanarchitecture.firstcomefirstserve.infrastructure.session;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.Session;

@Entity
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "host_name", nullable = false)
    String hostName;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "content", nullable = false)
    String content;

    @Column(name = "capacity", nullable = false)
    Integer capacity;

    @Column(name = "held_at", nullable = false)
    LocalDateTime heldAt;

    @Column(name = "num_registered_applicants", nullable = false)
    Integer numRegisteredApplicants;

    @Column(name = "is_closed", nullable = false)
    Boolean isClosed;

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    public static SessionEntity from(Session session) {
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.id = session.id();
        sessionEntity.hostName = session.hostName();
        sessionEntity.title = session.title();
        sessionEntity.content = session.content();
        sessionEntity.capacity = session.capacity();
        sessionEntity.heldAt = session.heldAt();
        sessionEntity.numRegisteredApplicants = session.numRegisteredApplicants();
        sessionEntity.isClosed = session.isClosed();
        sessionEntity.createdAt = session.createdAt();
        return sessionEntity;
    }

    public Session toModel() {
        return Session.builder()
            .id(id)
            .hostName(hostName)
            .title(title)
            .content(content)
            .capacity(capacity)
            .heldAt(heldAt)
            .numRegisteredApplicants(numRegisteredApplicants)
            .isClosed(isClosed)
            .createdAt(createdAt)
            .build();
    }
}
