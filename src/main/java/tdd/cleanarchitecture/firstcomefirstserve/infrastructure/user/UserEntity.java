package tdd.cleanarchitecture.firstcomefirstserve.infrastructure.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import tdd.cleanarchitecture.firstcomefirstserve.domain.user.User;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    public static UserEntity from(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.id = user.id();
        userEntity.createdAt = user.createdAt();
        return userEntity;
    }

    public User toModel() {
        return User.builder()
            .id(id)
            .createdAt(createdAt)
            .build();
    }
}
