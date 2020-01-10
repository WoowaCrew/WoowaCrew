package woowacrew.article.slack.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacrew.article.slack.TestSlackConfig;
import woowacrew.article.slack.domain.SlackConfig;
import woowacrew.article.slack.domain.SlackMessage;
import woowacrew.article.slack.domain.SlackMessageRepository;
import woowacrew.article.slack.dto.SlackMessageRequestDto;
import woowacrew.article.slack.exception.CreateSlackMessageFailException;
import woowacrew.article.slack.utils.SlackMessageConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.config.location=classpath:/slack.yml",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SlackMessageInternalServiceTest {

    @Autowired
    private TestSlackConfig testSlackConfig;

    @Mock
    private SlackMessageRepository slackMessageRepository;

    @Mock
    private SlackConfig slackConfig;

    @InjectMocks
    private SlackMessageInternalService slackMessageInternalService;

    private SlackMessage saveSlackMessage(String token, String channelId, String authorId) {
        SlackMessageRequestDto slackMessageRequestDto = new SlackMessageRequestDto(channelId, authorId, "hi", "test.com", "test2.com", false);
        SlackMessage slackMessage = SlackMessageConverter.toEntity("channelName", "authorName", slackMessageRequestDto);

        when(slackConfig.getToken()).thenReturn(token);
        when(slackMessageRepository.save(slackMessage)).thenReturn(slackMessage);

        return slackMessageInternalService.save(slackMessageRequestDto);
    }

    @Test
    @DisplayName("정상적으로 슬랙에서 온 메시지를 저장한다")
    void save() {
        String token = testSlackConfig.getToken();
        String channelId = testSlackConfig.getChannelId();
        String authorId = testSlackConfig.getAuthorId();

        SlackMessage result = saveSlackMessage(token, channelId, authorId);

        assertThat(result.getAuthor()).isEqualTo("authorName");
        assertThat(result.getChannel()).isEqualTo("channelName");
        assertThat(result.getContent()).isEqualTo("hi");
        assertThat(result.getDownloadLink()).isEqualTo("test.com");
        assertThat(result.getDownloadLinkFromSlack()).isEqualTo("test2.com");
    }

    @Test
    @DisplayName("존재하지 않는 토큰인 경우 저장하는데 실패한다.")
    void saveFailBecauseToken() {
        String invalidToken = "Invalid token";
        String channelId = testSlackConfig.getChannelId();
        String authorId = testSlackConfig.getAuthorId();

        assertThrows(CreateSlackMessageFailException.class, () -> saveSlackMessage(invalidToken, channelId, authorId));
    }

    @Test
    @DisplayName("존재하지 않는 채널 id인 경우 저장하는데 실패한다.")
    void saveFailBecauseChannelId() {
        String token = testSlackConfig.getToken();
        String invalidChannelId = "Invalid Channel Id";
        String authorId = testSlackConfig.getAuthorId();

        assertThrows(CreateSlackMessageFailException.class, () -> saveSlackMessage(token, invalidChannelId, authorId));
    }

    @Test
    @DisplayName("존재하지 않는 작성자 id인 경우 저장하는데 실패한다.")
    void saveFailBecauseAuthorId() {
        String token = testSlackConfig.getToken();
        String channelId = testSlackConfig.getChannelId();
        String invalidAuthorId = "invalid author id";

        assertThrows(CreateSlackMessageFailException.class, () -> saveSlackMessage(token, channelId, invalidAuthorId));
    }
}