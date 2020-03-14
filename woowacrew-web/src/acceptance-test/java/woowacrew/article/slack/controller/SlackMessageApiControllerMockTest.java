package woowacrew.article.slack.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import woowacrew.article.slack.dto.SlackMessageResponseDto;
import woowacrew.article.slack.exception.CreateSlackMessageFailException;
import woowacrew.article.slack.service.SlackMessageService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static woowacrew.article.slack.controller.SlackMessageApiControllerTest.requestSlackMessage;
import static woowacrew.article.slack.controller.SlackMessageApiControllerTest.requestSlackMessageWithFile;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class SlackMessageApiControllerMockTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SlackMessageService slackMessageService;

    @Test
    @DisplayName("정상적으로 슬랙 메시지를 저장한다.")
    void createSlackMessage() throws Exception {
        JSONObject body = requestSlackMessage("channelId", "authorId");

        given(slackMessageService.save(any())).willReturn(mock(SlackMessageResponseDto.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/slack")
                .contentType(APPLICATION_JSON_UTF8)
                .content(body.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(slackMessageService, times(1)).save(any());
    }

    @Test
    @DisplayName("정상적으로 파일이 포함된 슬랙 메시지를 저장한다.")
    void createSlackMessageWithFile() throws Exception {
        JSONObject request = requestSlackMessageWithFile("channelId", "authorId");

        given(slackMessageService.save(any())).willReturn(mock(SlackMessageResponseDto.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/slack")
                .contentType(APPLICATION_JSON_UTF8)
                .content(request.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(slackMessageService, times(1)).save(any());
    }

    @Test
    @DisplayName("올바르지 않은 채널 Id로 인해 슬랙 메세지를 저장하는데 실패한다.")
    void createSlackMessageFailBecauseChannelId() throws Exception {
        JSONObject request = requestSlackMessageWithFile("test", "authorId");

        given(slackMessageService.save(any())).willThrow(new CreateSlackMessageFailException());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/slack")
                .contentType(APPLICATION_JSON_UTF8)
                .content(request.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getClass()
                        .isAssignableFrom(CreateSlackMessageFailException.class)));
    }

    @Test
    @DisplayName("오늘 생일인 유저가 있으면 슬랙에 메세지를 보낸다")
    void sendMessageToBirthdayUser() throws Exception {
        LocalDate date = LocalDate.of(1995, 6, 8);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/slack/birthday-message")
                .contentType(APPLICATION_JSON_UTF8)
                .content(date.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(slackMessageService, times(1)).sendBirthdayMessage(any());
    }
}