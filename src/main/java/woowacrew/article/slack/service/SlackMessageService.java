package woowacrew.article.slack.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import woowacrew.article.slack.domain.SlackMessage;
import woowacrew.article.slack.dto.SlackMessageRequestDto;
import woowacrew.article.slack.dto.SlackMessageResponseDto;
import woowacrew.article.slack.dto.SlackMessageResponseDtos;
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

    public SlackMessageResponseDtos findAll(Pageable pageable) {
        Page<SlackMessage> slackMessages = slackMessageInternalService.findAll(pageable);
        return SlackMessageConverter.toDtos(slackMessages);
    }

    public SlackMessageResponseDto findById(Long id) {
        SlackMessage slackMessage = slackMessageInternalService.findById(id);
        return SlackMessageConverter.toDto(slackMessage);
    }

    public SlackMessageResponseDto findRecentlyMessage() {
        SlackMessage slackMessage = slackMessageInternalService.findRecentlyMessage();
        return SlackMessageConverter.toDto(slackMessage);
    }
}
