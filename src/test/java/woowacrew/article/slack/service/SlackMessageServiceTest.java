package woowacrew.article.slack.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import woowacrew.article.slack.domain.SlackMessage;
import woowacrew.article.slack.dto.SlackMessageRequestDto;
import woowacrew.article.slack.dto.SlackMessageResponseDto;
import woowacrew.article.slack.dto.SlackMessageResponseDtos;
import woowacrew.article.slack.utils.SlackMessageConverter;
import woowacrew.degree.domain.Degree;
import woowacrew.user.domain.User;
import woowacrew.user.service.UserInternalService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SlackMessageServiceTest {
    @Mock
    private SlackMessageInternalService slackMessageInternalService;

    @Mock
    private UserInternalService userInternalService;

    @InjectMocks
    private SlackMessageService slackMessageService;

    @Test
    @DisplayName("정상적으로 슬랙에서 온 메시지를 저장한다")
    void save() {
        SlackMessageRequestDto slackMessageRequestDto = new SlackMessageRequestDto("test", "testId", "hi", "test.com", "test2.com", false);
        SlackMessage slackMessage = SlackMessageConverter.toEntity("channelName", "authorName", slackMessageRequestDto);

        when(slackMessageInternalService.save(slackMessageRequestDto)).thenReturn(slackMessage);

        SlackMessageResponseDto savedSlackMessage = slackMessageService.save(slackMessageRequestDto);

        assertThat(savedSlackMessage.getAuthor()).isEqualTo("authorName");
        assertThat(savedSlackMessage.getChannel()).isEqualTo("channelName");
        assertThat(savedSlackMessage.getContent()).isEqualTo(slackMessageRequestDto.getContent());
        assertThat(savedSlackMessage.getDownloadLink()).isEqualTo(slackMessageRequestDto.getDownloadLink());
        assertThat(savedSlackMessage.getDownloadLinkFromSlack()).isEqualTo(slackMessageRequestDto.getDownloadLinkFromSlack());
    }

    @Test
    @DisplayName("정상적으로 슬랙 메세지들을 가져온다.")
    void list() {
        int slackMessageSize = 10;
        List<SlackMessage> slackMessages = createSlackMessages(slackMessageSize);

        when(slackMessageInternalService.findAll(any())).thenReturn(new PageImpl<>(slackMessages));

        SlackMessageResponseDtos result = slackMessageService.findAll(PageRequest.of(1, 20));
        assertEquals(result.getSlackMessages().size(), slackMessageSize);
    }

    @Test
    @DisplayName("정상적으로 ID로 슬랙 메세지를 찾는다.")
    void findById() {
        SlackMessage slackMessage = createSlackMessages(1).get(0);

        when(slackMessageInternalService.findById(1L)).thenReturn(slackMessage);

        SlackMessageResponseDto result = slackMessageService.findById(1L);

        assertThat(result.getId()).isEqualTo(slackMessage.getId());
        assertThat(result.getChannel()).isEqualTo(slackMessage.getChannel());
        assertThat(result.getAuthor()).isEqualTo(slackMessage.getAuthor());
        assertThat(result.getContent()).isEqualTo(slackMessage.getContent());
        assertThat(result.getDownloadLink()).isEqualTo(slackMessage.getDownloadLink());
        assertThat(result.getDownloadLinkFromSlack()).isEqualTo(slackMessage.getDownloadLinkFromSlack());
    }

    @Test
    @DisplayName("정상적으로 최근 슬랙 메세지를 가져온다.")
    void findRecentlyMessage() {
        SlackMessage slackMessage = createSlackMessages(1).get(0);

        when(slackMessageInternalService.findRecentlyMessage()).thenReturn(slackMessage);

        assertNotNull(slackMessageService.findRecentlyMessage());
    }

    @Test
    @DisplayName("정상적으로 최근 슬랙 공지사항 메세지를 가져온다.")
    void findRecentlyNoticeMessage() {
        SlackMessage slackMessage = new SlackMessage("전체-공지사항","author", "content", "test.com", "test2.com");

        when(slackMessageInternalService.findRecentlyNoticeMessage()).thenReturn(slackMessage);
        SlackMessageResponseDto response = slackMessageService.findRecentlyNoticeMessage();

        assertThat(response.getChannel()).isEqualTo("전체-공지사항");
    }

    private List<SlackMessage> createSlackMessages(int slackMessageSize) {
        List<SlackMessage> slackMessages = new ArrayList<>();
        for (int i = 0; i < slackMessageSize; i++) {
            SlackMessage slackMessage = new SlackMessage("channel", "author", "content", "test.com", "test2.com");
            slackMessages.add(slackMessage);
        }
        return slackMessages;
    }

    @Test
    @DisplayName("오늘 생일자가 없으면 슬랙 메세지를 보내지 않는다")
    void sendBirthdayMessageFail() {
        when(userInternalService.findBirthdayBy(any())).thenReturn(new ArrayList<>());

        slackMessageService.sendBirthdayMessage(LocalDate.now());
        verify(slackMessageInternalService, times(0)).sendMessage(any());
    }

    @Test
    @DisplayName("오늘 생일자가 있으면 슬랙 메세지를 보낸다")
    void sendBirthdayMessage() {
        when(userInternalService.findBirthdayBy(any())).thenReturn(Collections.singletonList(new User("test", new Degree())));

        slackMessageService.sendBirthdayMessage(LocalDate.now());
        verify(slackMessageInternalService, times(1)).sendMessage(any());
    }
}