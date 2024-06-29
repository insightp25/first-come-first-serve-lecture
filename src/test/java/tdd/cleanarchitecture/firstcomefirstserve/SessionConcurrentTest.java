package tdd.cleanarchitecture.firstcomefirstserve;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
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

    private static final int NUM_PARTICIPANTS_50 = 50;
    private static final int MAX_CAPACITY_30 = 30;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private SessionService sessionService;

    /**
     * 본 테스트 클래스에서 롤백 등을 위해 트랜잭션 매니저를 활용하게 될 경우, 테스트 설계상 동일 메서드 내 given 영역에서 사전에 삽입되고 설정상 읽을 수 있었어야 할 데이터(커밋되지 않은 상태)를 when, then 영역에서 생성되는 비동기 스레드 들이 읽을 수 없게되므로, 테스트에서 트랜잭션 매니저 혹은 @Transactional 을 의도적으로 사용하지 않거나 별도의 조치가 필요하게 합니다.
     * 롤백이 안되므로 로우가 계속 축적되며, H2 인메모리 모드와 같이 DB가 테스트후 DB 내용을 모두 파기하지 않는 영속성을 사용시 추가적인 주의와 관리가 필요합니다.
     */
    @Test
    public void 정원_이상이_동시_수강신청시_정원_수_만큼만_수강을_허용한다() {
        // given
        List<User> users = new ArrayList<>();
        for (int i = 0; i < NUM_PARTICIPANTS_50; i++) {
            User user = userRepository.save(User.builder()
                .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build());
            users.add(user);
        }

        Lecture lecture = lectureRepository.save(LectureEntity.from(Lecture.builder()
            .hostName("John Doe")
            .title("Introduction to Spring Framework")
            .content("This is a special lecture introducing Spring Framework")
            .capacity(MAX_CAPACITY_30)
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
        for (int i = 0; i < NUM_PARTICIPANTS_50; i++) {
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
        Assertions.assertThat(result.numRegisteredApplicants()).isEqualTo(MAX_CAPACITY_30);
    }

    @Test
    @Transactional
    void 정원_이상이_차례대로_수강신청시_정원_수_만큼만_수강을_허용한다() {
        // given
        List<User> users = new ArrayList<>();
        for (int i = 0; i < NUM_PARTICIPANTS_50; i++) {
            User user = userRepository.save(User.builder()
                .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build());
            users.add(user);
        }

        Lecture lecture = lectureRepository.save(LectureEntity.from(Lecture.builder()
            .hostName("John Doe")
            .title("Introduction to Spring Framework")
            .content("This is a special lecture introducing Spring Framework")
            .capacity(MAX_CAPACITY_30)
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build())).toModel();

        Session session = sessionRepository.save(Session.builder()
            .lecture(lecture)
            .numRegisteredApplicants(0)
            .startsAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(3))
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).minusDays(3))
            .build());

        // when & then
        for (int i = 0; i < NUM_PARTICIPANTS_50; i++) {
            try {
                sessionService.register(users.get(i).id(), session.id());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        Session result = sessionService.findByIdLocked_ForTestPurposeOnly(session.id());
        Assertions.assertThat(result.numRegisteredApplicants()).isEqualTo(MAX_CAPACITY_30);
    }
}
