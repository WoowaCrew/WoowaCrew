package woowacrew.article.slack.service;

import org.springframework.stereotype.Service;
import woowacrew.article.slack.domain.SlackMessage;
import woowacrew.article.slack.dto.SlackMessageRequestDto;
import woowacrew.article.slack.dto.SlackMessageResponseDto;
import woowacrew.article.slack.utils.SlackMessageConverter;

@Service
public class SlackMessageService {
    private SlackMessageInternalService slackMessageInternalService;

    public SlackMessageService(SlackMessageInternalService slackMessageInternalService) {
        this.slackMessageInternalService = slackMessageInternalService;
    }

    public SlackMessageResponseDto save(SlackMessageRequestDto slackMessageRequestDto) {
        SlackMessage slackMessage = slackMessageInternalService.save(slackMessageRequestDto);
        return SlackMessageConverter.toDto(slackMessage);
    }
}
