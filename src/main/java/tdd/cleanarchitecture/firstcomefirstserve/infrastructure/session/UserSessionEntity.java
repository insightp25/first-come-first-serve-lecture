package tdd.cleanarchitecture.firstcomefirstserve.infrastructure.session;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.UserSession;
import tdd.cleanarchitecture.firstcomefirstserve.infrastructure.user.UserEntity;

@Entity
@IdClass(UserSessionEntityId.class)
public class UserSessionEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    SessionEntity sessionEntity;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity userEntity;

    @Column(nullable = false, name = "is_registered")
    Boolean isRegistered;

    @Column(nullable = false, name = "created_at")
    LocalDateTime createdAt;

    public static UserSessionEntity from(
        UserSession userSession
    ) {
        UserSessionEntity userSessionEntity = new UserSessionEntity();
        userSessionEntity.userEntity = UserEntity.from(userSession.user());
        userSessionEntity.sessionEntity = SessionEntity.from(userSession.session());
        userSessionEntity.isRegistered = userSession.isRegistered();
        userSessionEntity.createdAt = userSession.createdAt();
        return userSessionEntity;
    }

    public UserSession toModel() {
        return UserSession.builder()
            .user(userEntity.toModel())
            .session(sessionEntity.toModel())
            .isRegistered(isRegistered)
            .createdAt(createdAt)
            .build();
    }
}
