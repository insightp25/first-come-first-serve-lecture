package tdd.cleanarchitecture.firstcomefirstserve.infrastructure.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tdd.cleanarchitecture.firstcomefirstserve.domain.user.User;
import tdd.cleanarchitecture.firstcomefirstserve.domain.user.port.UserRepository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.from(user)).toModel();
    }

    public User findById(Long userId) {
        return userJpaRepository.findById(userId).orElseThrow().toModel();
    }
}
