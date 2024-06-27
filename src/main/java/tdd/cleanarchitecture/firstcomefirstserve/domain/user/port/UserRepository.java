package tdd.cleanarchitecture.firstcomefirstserve.domain.user.port;

import tdd.cleanarchitecture.firstcomefirstserve.domain.user.User;

public interface UserRepository {

    User save(User user);

    User findById(Long userId);
}
