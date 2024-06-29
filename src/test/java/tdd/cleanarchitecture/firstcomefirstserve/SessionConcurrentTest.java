package tdd.cleanarchitecture.firstcomefirstserve;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import tdd.cleanarchitecture.firstcomefirstserve.controller.session.port.SessionService;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.Lecture;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.Session;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.SessionServiceImpl;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.exception.SessionUnavailableException;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.port.LectureRepository;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.port.SessionRepository;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.port.UserSessionRepository;
import tdd.cleanarchitecture.firstcomefirstserve.domain.user.User;
import tdd.cleanarchitecture.firstcomefirstserve.domain.user.port.UserRepository;
import tdd.cleanarchitecture.firstcomefirstserve.infrastructure.session.LectureEntity;

@SpringBootTest
public class SessionConcurrentTest {

    private static final int NUM_PARTICIPANTS_20 = 20;
    private static final int NUM_PARTICIPANTS_2 = 2;
    private static final int MAX_CAPACITY_10 = 10;
    private static final int MAX_CAPACITY_1 = 1;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private TransactionStatus status;
    private List<User> users;
    private Lecture lecture;
    private Session session;

    @BeforeEach
    void setUp() {
        status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        // - 여기에서 데이터를 DB에 미리 저장해두지 않고 각 테스트 메서드마다 저장할 경우 아래와 같운 문제가 발생합니다. 따라서 이렇게 테스트를 구현하거나, 혹은 메서드별로 데이터를 삽입할 시 트랜잭션 매니저를 사용하지 않도록 해야합니다. 후자의 경우 본 클래스에서 다른 테스트 메서드들을 추가할 수 없게 됩니다.
        // - 메서드별로 데이터를 삽입할 시:
        //   1. 본 테스트 클래스에서 롤백 등을 위해 트랜잭션 매니저를 활용하게 될 경우, 테스트 설계상 동일 메서드 내 given 영역에서 사전에 삽입되고 설정상 읽을 수 있었어야 할 데이터(커밋되지 않은 상태)를 when, then 영역에서 생성되는 비동기 스레드 들이 읽을 수 없게되므로, 트랜잭션 매니저를 의도적으로 사용하지 않아야 합니다.
        //   2. 롤백이 안될시 여러 테스트 메서드를 함께 실행할 경우 각 테스트 메서드가 활용하는 DB 데이터가 섞이게 되므로, 본 테스트 클래스의 테스트 메서드는 하나만 구현해야 하게 됩니다.
        users = new ArrayList<>();
        for (int i = 0; i < NUM_PARTICIPANTS_20; i++) {
            User user = userRepository.save(User.builder()
                .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build());
            users.add(user);
        }

        lecture = lectureRepository.save(LectureEntity.from(Lecture.builder()
            .hostName("John Doe")
            .title("Introduction to Spring Framework")
            .content("This is a special lecture introducing Spring Framework")
            .capacity(MAX_CAPACITY_10)
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build())).toModel();

        session = sessionRepository.save(Session.builder()
            .lecture(lecture)
            .numRegisteredApplicants(0)
            .startsAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(3))
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).minusDays(3))
            .build());
    }

    @AfterEach
    void rollBack() {
        transactionManager.rollback(status);
    }

    @Test
    public void 정원_이상이_동시_수강신청시_정원_수_만큼만_수강을_허용한다() throws Exception {
        // given
        // when
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 0; i < NUM_PARTICIPANTS_20; i++) {
            final long userId = users.get(i).id();
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    sessionService.register(userId, session.id());
                } catch (SessionUnavailableException e) {
                    System.out.println(e.getMessage());
                }
            });

            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // then
        // 테스트 전용 더미 메서드 활용
        Session result = sessionService.findByIdLocked_ForTestPurposeOnly(session.id());
        Assertions.assertThat(result.numRegisteredApplicants()).isEqualTo(MAX_CAPACITY_10);
    }

    @Test
    void 정원_이상이_차례대로_수강신청시_정원_수_만큼만_수강을_허용한다() throws Exception {
        // given
        // when & then
        for (int i = 0; i < NUM_PARTICIPANTS_20; i++) {
            try {
                sessionService.register(users.get(i).id(), session.id());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        Session result = sessionService.findByIdLocked_ForTestPurposeOnly(session.id());
        Assertions.assertThat(result.numRegisteredApplicants()).isEqualTo(MAX_CAPACITY_10);
    }
}
