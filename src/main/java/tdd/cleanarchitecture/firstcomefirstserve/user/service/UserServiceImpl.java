package tdd.cleanarchitecture.firstcomefirstserve.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.cleanarchitecture.firstcomefirstserve.user.domain.User;
import tdd.cleanarchitecture.firstcomefirstserve.user.service.port.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }
}
