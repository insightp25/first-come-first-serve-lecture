package tdd.cleanarchitecture.firstcomefirstserve.infrastructure.session;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.Lecture;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.Session;

@Entity
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "starts_at", nullable = false)
    LocalDateTime startsAt;

    @Column(name = "num_registered_applicants", nullable = false)
    Integer numRegisteredApplicants;

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "lecture_id", nullable = false)
    LectureEntity lectureEntity;

    @OneToMany(mappedBy = "sessionEntity")
    List<UserSessionEntity> userSessionEntities = new ArrayList<>();

    public static SessionEntity from(Session session) {
        LectureEntity innerLectureEntity = new LectureEntity();
        innerLectureEntity.id = session.lecture().id();
        innerLectureEntity.hostName = session.lecture().hostName();
        innerLectureEntity.title = session.lecture().title();
        innerLectureEntity.content = session.lecture().content();
        innerLectureEntity.capacity = session.lecture().capacity();
        innerLectureEntity.createdAt = session.lecture().createdAt();

        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.id = session.id();
        sessionEntity.startsAt = session.startsAt();
        sessionEntity.numRegisteredApplicants = session.numRegisteredApplicants();
        sessionEntity.createdAt = session.createdAt();
        sessionEntity.lectureEntity = innerLectureEntity;

        return sessionEntity;
    }

    public Session toModel() {
        return Session.builder()
            .id(id)
            .startsAt(startsAt)
            .numRegisteredApplicants(numRegisteredApplicants)
            .createdAt(createdAt)
            .lecture(Lecture.builder()
                .id(lectureEntity.id)
                .hostName(lectureEntity.hostName)
                .title(lectureEntity.title)
                .content(lectureEntity.title)
                .capacity(lectureEntity.capacity)
                .createdAt(lectureEntity.createdAt)
                .build())
            .build();
    }
}
