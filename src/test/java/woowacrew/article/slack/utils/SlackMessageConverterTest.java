package woowacrew.article.slack.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacrew.article.slack.domain.SlackMessage;
import woowacrew.article.slack.dto.SlackMessageRequestDto;
import woowacrew.article.slack.dto.SlackMessageResponseDto;

import static org.assertj.core.api.Assertions.assertThat;

class SlackMessageConverterTest {
    @Test
    @DisplayName("정상적으로 RequestDto에서 Entity로 변환한다.")
    void toEntity() {
        SlackMessageRequestDto slackMessageRequestDto = new SlackMessageRequestDto("test", "kim ", "hi", "test.com", "test2.com", false);

        SlackMessage result = SlackMessageConverter.toEntity("channelName", "authorName", slackMessageRequestDto);

        assertThat(result.getChannel()).isEqualTo("channelName");
        assertThat(result.getAuthor()).isEqualTo("authorName");
        assertThat(result.getContent()).isEqualTo(slackMessageRequestDto.getContent());
        assertThat(result.getDownloadLink()).isEqualTo(slackMessageRequestDto.getDownloadLink());
        assertThat(result.getDownloadLinkFromSlack()).isEqualTo(slackMessageRequestDto.getDownloadLinkFromSlack());
    }

    @Test
    @DisplayName("정상적으로 Entity에서 ResponseDto로 변환한다.")
    void toDto() {
        SlackMessage slackMessage = new SlackMessage("test", "kim ", "hi", "test.com", "test2.com");

        SlackMessageResponseDto result = SlackMessageConverter.toDto(slackMessage);

        assertThat(result.getAuthor()).isEqualTo(slackMessage.getAuthor());
        assertThat(result.getContent()).isEqualTo(slackMessage.getContent());
        assertThat(result.getChannel()).isEqualTo(slackMessage.getChannel());
        assertThat(result.getDownloadLink()).isEqualTo(slackMessage.getDownloadLink());
        assertThat(result.getDownloadLinkFromSlack()).isEqualTo(slackMessage.getDownloadLinkFromSlack());
    }
}