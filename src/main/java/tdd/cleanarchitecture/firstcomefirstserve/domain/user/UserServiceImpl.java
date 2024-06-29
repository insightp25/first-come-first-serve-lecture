package tdd.cleanarchitecture.firstcomefirstserve.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.cleanarchitecture.firstcomefirstserve.domain.user.port.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }
}
