package tdd.cleanarchitecture.firstcomefirstserve.user.service.port;

import tdd.cleanarchitecture.firstcomefirstserve.user.domain.User;

public interface UserRepository {

    User save(User user);

    User findById(Long userId);
}
