package tdd.cleanarchitecture.firstcomefirstserve;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import tdd.cleanarchitecture.firstcomefirstserve.controller.session.port.SessionService;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.Lecture;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.Session;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.exception.SessionUnavailableException;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.port.LectureRepository;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.port.SessionRepository;
import tdd.cleanarchitecture.firstcomefirstserve.domain.user.User;
import tdd.cleanarchitecture.firstcomefirstserve.domain.user.port.UserRepository;
import tdd.cleanarchitecture.firstcomefirstserve.infrastructure.session.LectureEntity;

@SpringBootTest
public class SessionConcurrentTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private SessionService sessionService;

    private TransactionStatus status;

    @BeforeEach
    void setUp() {
        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    @AfterEach
    void rollBack() {
        transactionManager.rollback(status);
    }

    private static final int NUM_PARTICIPANTS_20 = 20;
    private static final int NUM_PARTICIPANTS_2 = 2;
    private static final int MAX_CAPACITY_10 = 10;
    private static final int MAX_CAPACITY_1 = 1;

    @Test
    public void 정원_이상이_동시_수강신청시_정원_수_만큼만_수강을_허용한다() throws Exception {
        // given
        List<User> users = new ArrayList<>();
        for (int i = 0; i < NUM_PARTICIPANTS_2; i++) {
            User user = userRepository.save(User.builder()
                .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build());
            users.add(user);
        }

        Lecture lecture = lectureRepository.save(LectureEntity.from(Lecture.builder()
            .hostName("John Doe")
            .title("Introduction to Spring Framework")
            .content("This is a special lecture introducing Spring Framework")
            .capacity(MAX_CAPACITY_1)
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build())).toModel();

        Session session = sessionRepository.save(Session.builder()
            .lecture(lecture)
            .numRegisteredApplicants(0)
            .startsAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(3))
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).minusDays(3))
            .build());

        // when
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 0; i < NUM_PARTICIPANTS_2; i++) {
            final long userId = users.get(i).id();

            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                sessionService.register(userId, session.id());
            });

            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // then
        Session result = sessionRepository.findByIdLocked(session.id()).orElseThrow();
        Assertions.assertThat(result.numRegisteredApplicants()).isEqualTo(MAX_CAPACITY_1);
    }

    @Test
    void 정원_이상이_차례대로_수강신청시_정원_수_만큼만_수강을_허용한다() throws Exception {
        // given
        List<User> users = new ArrayList<>();

        for (int i = 0; i < NUM_PARTICIPANTS_20; i++) {
            User user = userRepository.save(User.builder()
                .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build());
            users.add(user);
        }

        Lecture lecture = lectureRepository.save(LectureEntity.from(Lecture.builder()
            .hostName("John Doe")
            .title("Introduction to Spring Framework")
            .content("This is a special lecture introducing Spring Framework")
            .capacity(MAX_CAPACITY_10)
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build())).toModel();

        Session session = sessionRepository.save(Session.builder()
            .lecture(lecture)
            .numRegisteredApplicants(0)
            .startsAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(3))
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).minusDays(3))
            .build());

        // when & then
        for (int i = 0; i < NUM_PARTICIPANTS_20; i++) {
            try {
                sessionService.register(users.get(i).id(), session.id());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        Session result = sessionRepository.findByIdLocked(session.id()).orElseThrow();
        Assertions.assertThat(result.numRegisteredApplicants()).isEqualTo(MAX_CAPACITY_10);
    }
}
