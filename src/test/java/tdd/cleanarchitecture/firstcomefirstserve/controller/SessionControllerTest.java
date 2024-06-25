package tdd.cleanarchitecture.firstcomefirstserve.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tdd.cleanarchitecture.firstcomefirstserve.controller.port.SessionService;
import tdd.cleanarchitecture.firstcomefirstserve.domain.Session;
import tdd.cleanarchitecture.firstcomefirstserve.domain.SessionApplicationHistory;
import tdd.cleanarchitecture.firstcomefirstserve.domain.exception.SessionUnavailableException;
import tdd.cleanarchitecture.firstcomefirstserve.service.SessionServiceImpl;

@WebMvcTest(SessionController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SessionService sessionService;

    @Test
    public void 특강이_신청기간_내이고_잔여_정원이_남아있을시_특정_유저가_수강신청을_하면_수강등록_후_강의정보를_반환한다() throws Exception {
        // given
        given(sessionService.apply(anyLong(), anyLong()))
            .willReturn(Session.builder().build());

        // when & then
        mockMvc.perform(post("/sessions/{session_id}/application", 999L)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("user_id", String.valueOf(999L)))
            .andExpect(status().isOk());
    }

    @Test
    public void 특강의_신청기간이_지났거나_잔여_정원이_꽉찼을시_특정_유저가_수강신청을_하면_실패를_반환한다() throws Exception {
        // given
        given(sessionService.apply(anyLong(), anyLong())).willThrow(
            new SessionUnavailableException());

        // when & then
        mockMvc.perform(post("/sessions/{session_id}/application", 999L)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("user_id", String.valueOf(999L)))
            .andExpect(status().isConflict());
    }

    @Test
    public void 신청_가능한_특강_목록을_조회할_수_있다() throws Exception {
        // given
        List<Session> sessions = new ArrayList<>(List.of(Session.builder().build()));
        given(sessionService.searchAvailable()).willReturn(sessions);

        // when & then
        MvcResult mvcResult = mockMvc.perform(get("/sessions")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<Session> result = objectMapper.readValue(contentAsString, new TypeReference<>() {});

        Assertions.assertThat(result)
            .isEqualTo(new ArrayList<>(List.of(Session.builder().build())));
    }

    @Test
    public void 특정_유저의_특강_신청_결과_목록을_조회할_수_있다() throws Exception {
        // given
        List<SessionApplicationHistory> sessionApplicationHistory =
            new ArrayList<>(List.of(SessionApplicationHistory.builder().build()));
        given(sessionService.searchApplicationHistory()).willReturn(sessionApplicationHistory);

        // when & then
        MvcResult mvcResult = mockMvc.perform(get("/sessions/application/{userId}", 999L)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<SessionApplicationHistory> result = objectMapper.readValue(contentAsString, new TypeReference<>() {});

        Assertions.assertThat(result)
            .isEqualTo(new ArrayList<>(List.of(SessionApplicationHistory.builder().build())));
    }
}
