package woowacrew.article.slack.utils;

import woowacrew.article.slack.domain.SlackMessage;
import woowacrew.article.slack.dto.SlackMessageRequestDto;
import woowacrew.article.slack.dto.SlackMessageResponseDto;

public class SlackMessageConverter {
    private SlackMessageConverter() {
    }

    public static SlackMessage toEntity(String channelName, String authorName, SlackMessageRequestDto slackMessageRequestDto) {
        return new SlackMessage(
                channelName,
                authorName,
                slackMessageRequestDto.getContent(),
                slackMessageRequestDto.getDownloadLink(),
                slackMessageRequestDto.getDownloadLinkFromSlack());
    }

    public static SlackMessageResponseDto toDto(SlackMessage slackMessage) {
        return new SlackMessageResponseDto(
                slackMessage.getId(),
                slackMessage.getChannel(),
                slackMessage.getAuthor(),
                slackMessage.getContent(),
                slackMessage.getDownloadLink(),
                slackMessage.getDownloadLinkFromSlack());
    }
}
