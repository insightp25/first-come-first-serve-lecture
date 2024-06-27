package tdd.cleanarchitecture.firstcomefirstserve.domain.session;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.exception.SessionNotFoundException;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.exception.SessionUnavailableException;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.port.SessionApplicationHistoryRepository;
import tdd.cleanarchitecture.firstcomefirstserve.domain.session.port.SessionRepository;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SessionServiceImplUnitTest {

    @InjectMocks
    private SessionServiceImpl sessionServiceImpl;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private SessionApplicationHistoryRepository sessionApplicationHistoryRepository;

    @Test
    public void 특강이_신청기간_내이고_잔여_정원이_남아있을시_특정_유저가_수강신청을_하면_수강등록_후_강의정보를_반환한다() {
        // given
        given(sessionRepository.findById(anyLong())).willReturn(Optional.of(Session.builder()
            .numRegisteredApplicants(0)
            .heldAt(LocalDateTime.of(999999999, 6, 30, 23, 59, 59))
            .capacity(30)
            .build()));
        given(sessionRepository.save(any())).willReturn(Session.builder().build());

        // when
        Session result = sessionServiceImpl.apply(999L, 999L);

        // then
        Assertions.assertThat(result).isEqualTo(Session.builder().build());
    }

    @Test
    public void 특강이_존재하지_않을시_특정_유저가_수강신청을_하면_오류를_반환한다() {
        // given
        given(sessionRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        Assertions.assertThatThrownBy(() -> {
            sessionServiceImpl.apply(999L, 999L);
        }).isInstanceOf(SessionNotFoundException.class);
    }

    @Test
    public void 특강의_신청기간이_지났을시_특정_유저가_수강신청을_하면_오류를_반환한다() {
        // given
        given(sessionRepository.findById(anyLong())).willReturn(Optional.of(Session.builder()
            .numRegisteredApplicants(0)
            .heldAt(LocalDateTime.of(-999999999, 1, 1, 0, 0, 0))
            .capacity(30)
            .build()));

        // when & then
        Assertions.assertThatThrownBy(() -> {
            sessionServiceImpl.apply(999L, 999L);
        }).isInstanceOf(SessionUnavailableException.class);
    }

    @Test
    public void 특강의_정원을_초과했을_시_특정_유저가_수강신청을_하면_오류를_반환한다() {
        // given
        given(sessionRepository.findById(anyLong())).willReturn(Optional.of(Session.builder()
            .numRegisteredApplicants(Integer.MAX_VALUE)
            .capacity(30)
            .build()));

        // when & then
        Assertions.assertThatThrownBy(() -> {
            sessionServiceImpl.apply(999L, 999L);
        }).isInstanceOf(SessionUnavailableException.class);
    }

    @Test
    public void 신청_가능한_특강_목록을_조회할_수_있다() {
        // given
        LocalDateTime futureDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(1);
        given(sessionRepository.getAll()).willReturn(new ArrayList<>(List.of(Session.builder()
            .heldAt(futureDate)
            .capacity(30)
            .numRegisteredApplicants(0)
            .build())));

        // when
        List<Session> result = sessionServiceImpl.searchAllAvailable();

        // then
        Assertions.assertThat(result).isEqualTo(new ArrayList<>(List.of(Session.builder()
            .heldAt(futureDate)
            .capacity(30)
            .numRegisteredApplicants(0)
            .build())));
    }
}