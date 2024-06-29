package tdd.cleanarchitecture.firstcomefirstserve.infrastructure.session;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.Lecture;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.Session;

@Entity
public class LectureEntity {

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

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @OneToMany(mappedBy = "lectureEntity")
    List<SessionEntity> sessionsEntities = new ArrayList<>();

    public static LectureEntity from(Lecture lecture) {
        LectureEntity lectureEntity = new LectureEntity();
        lectureEntity.id = lecture.id();
        lectureEntity.hostName = lecture.hostName();
        lectureEntity.title = lecture.title();
        lectureEntity.content = lecture.content();
        lectureEntity.capacity = lecture.capacity();
        lectureEntity.createdAt = lecture.createdAt();
        return lectureEntity;
    }

    public Lecture toModel() {
        return Lecture.builder()
            .id(id)
            .hostName(hostName)
            .title(title)
            .content(content)
            .capacity(capacity)
            .createdAt(createdAt)
            .build();
    }
}
