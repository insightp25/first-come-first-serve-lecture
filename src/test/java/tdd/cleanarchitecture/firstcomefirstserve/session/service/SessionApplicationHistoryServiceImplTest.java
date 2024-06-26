package tdd.cleanarchitecture.firstcomefirstserve.session.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.cleanarchitecture.firstcomefirstserve.session.domain.SessionApplicationHistory;
import tdd.cleanarchitecture.firstcomefirstserve.session.service.port.SessionApplicationHistoryRepository;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class SessionApplicationHistoryServiceImplTest {

    @InjectMocks
    SessionApplicationHistoryServiceImpl sessionApplicationHistoryServiceImpl;

    @Mock
    private SessionApplicationHistoryRepository sessionApplicationHistoryRepository;

    @Test
    public void 특정_유저의_특강_신청_결과_목록을_조회할_수_있다() {
        // given
        given(sessionApplicationHistoryRepository.findAllByUserId(anyLong()))
            .willReturn(new ArrayList<>(List.of(SessionApplicationHistory.builder().build())));

        // when
        List<SessionApplicationHistory> result =
            sessionApplicationHistoryServiceImpl.searchSessionApplicationHistory(99L);

        // then
        Assertions.assertThat(result)
            .isEqualTo(new ArrayList<>(List.of(SessionApplicationHistory.builder().build())));
    }
}
