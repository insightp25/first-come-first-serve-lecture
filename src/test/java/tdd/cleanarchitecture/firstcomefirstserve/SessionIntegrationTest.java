package tdd.cleanarchitecture.firstcomefirstserve;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.Session;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.SessionApplicationHistory;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.SessionApplicationHistoryId;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.port.SessionApplicationHistoryRepository;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.port.SessionRepository;
import tdd.cleanarchitecture.firstcomefirstserve.domain.user.User;
import tdd.cleanarchitecture.firstcomefirstserve.domain.user.port.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class SessionIntegrationTest {

    private static final int MAX_CAPACITY = 30;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionApplicationHistoryRepository sessionApplicationHistoryRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private TransactionStatus status;


    @BeforeEach
    void setUp() {
        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    @AfterEach
    void rollBack() {
        transactionManager.rollback(status);
    }

    @Test
    public void 특강이_신청기간_내이고_잔여_정원이_남아있을시_특정_유저가_수강신청을_하면_수강등록_후_강의정보를_반환한다() throws Exception {
        // given
        User user = userRepository.save(User.builder()
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build());

        Session session = sessionRepository.save(Session.builder()
            .hostName("John Doe")
            .title("Introduction to Spring Framework")
            .content("This is a special lecture introducing Spring Framework")
            .numRegisteredApplicants(0)
            .capacity(MAX_CAPACITY)
            .heldAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(3))
            .isClosed(false)
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).minusDays(3))
            .build());

        // when & then
        mockMvc.perform(post("/sessions/{session_id}/application", session.id())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("user_id", String.valueOf(user.id())))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void 특강의_신청_정원이_꽉찼을시_특정_유저가_수강신청을_하면_실패를_반환한다() throws Exception {
        // given

        User user = userRepository.save(User.builder()
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build());

        Session session = sessionRepository.save(Session.builder()
            .hostName("John Doe")
            .title("Introduction to Spring Framework")
            .content("This is a special lecture introducing Spring Framework")
            .numRegisteredApplicants(MAX_CAPACITY)
            .capacity(MAX_CAPACITY)
            .heldAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(3))
            .isClosed(true)
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).minusDays(3))
            .build());

        // when & then
        mockMvc.perform(post("/sessions/{session_id}/application", session.id())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("user_id", String.valueOf(user.id())))
            .andDo(print())
            .andExpect(status().isConflict());
    }

    @Test
    public void 특강의_신청기간이_지났을시_특정_유저가_수강신청을_하면_실패를_반환한다() throws Exception {
        // given
        User user = userRepository.save(User.builder()
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build());

        Session session = sessionRepository.save(Session.builder()
            .hostName("John Doe")
            .title("Introduction to Spring Framework")
            .content("This is a special lecture introducing Spring Framework")
            .numRegisteredApplicants(0)
            .capacity(MAX_CAPACITY)
            .heldAt(LocalDateTime.MIN.truncatedTo(ChronoUnit.SECONDS))
            .isClosed(true)
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).minusDays(3))
            .build());

        // when & then
        mockMvc.perform(post("/sessions/{session_id}/application", session.id())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("user_id", String.valueOf(user.id())))
            .andDo(print())
            .andExpect(status().isConflict());
    }

    @Test
    public void 신청_가능한_특강_목록을_조회할_수_있다() throws Exception {
        // given
        User user = userRepository.save(User.builder()
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build());

        Session session1 = sessionRepository.save(Session.builder()
            .hostName("John Doe")
            .title("Introduction to Spring Framework")
            .content("This is a special lecture introducing Spring Framework")
            .numRegisteredApplicants(0)
            .capacity(MAX_CAPACITY)
            .heldAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(3))
            .isClosed(false)
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).minusDays(3))
            .build());

        Session session2 = sessionRepository.save(Session.builder()
            .hostName("John Doe")
            .title("Introduction to FastAPI Framework")
            .content("This is a special lecture introducing FastAPI Framework")
            .numRegisteredApplicants(MAX_CAPACITY)
            .capacity(MAX_CAPACITY)
            .heldAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(3))
            .isClosed(true)
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).minusDays(3))
            .build());

        sessionApplicationHistoryRepository.save(SessionApplicationHistory.builder()
            .sessionApplicationHistoryId(SessionApplicationHistoryId.builder()
                .sessionId(session1.id())
                .userId(user.id())
                .build())
            .isRegistered(true)
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build());

        sessionApplicationHistoryRepository.save(SessionApplicationHistory.builder()
            .sessionApplicationHistoryId(SessionApplicationHistoryId.builder()
                .sessionId(session2.id())
                .userId(user.id())
                .build())
            .isRegistered(false)
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build());

        // when & then
        mockMvc.perform(get("/sessions")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
    }

    @Test
    public void 특정_유저의_특강_신청_결과_목록을_조회할_수_있다() throws Exception {
        // given
        User user = userRepository.save(User.builder()
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build());

        Session session1 = sessionRepository.save(Session.builder()
            .hostName("John Doe")
            .title("Introduction to Spring Framework")
            .content("This is a special lecture introducing Spring Framework")
            .numRegisteredApplicants(0)
            .capacity(MAX_CAPACITY)
            .heldAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(3))
            .isClosed(false)
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).minusDays(3))
            .build());

        Session session2 = sessionRepository.save(Session.builder()
            .hostName("John Doe")
            .title("Introduction to FastAPI Framework")
            .content("This is a special lecture introducing FastAPI Framework")
            .numRegisteredApplicants(MAX_CAPACITY)
            .capacity(MAX_CAPACITY)
            .heldAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(3))
            .isClosed(true)
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).minusDays(3))
            .build());

        sessionApplicationHistoryRepository.save(SessionApplicationHistory.builder()
            .sessionApplicationHistoryId(SessionApplicationHistoryId.builder()
                .sessionId(session1.id())
                .userId(user.id())
                .build())
            .isRegistered(true)
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build());

        sessionApplicationHistoryRepository.save(SessionApplicationHistory.builder()
            .sessionApplicationHistoryId(SessionApplicationHistoryId.builder()
                .sessionId(session2.id())
                .userId(user.id())
                .build())
            .isRegistered(false)
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build());

        // when & then
        mockMvc.perform(get("/sessions/application/{userId}", user.id())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
    }
}
