package woowacrew.article.slack.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import woowacrew.article.slack.domain.SlackMessage;
import woowacrew.article.slack.dto.SlackMessageRequestDto;
import woowacrew.article.slack.dto.SlackMessageResponseDto;
import woowacrew.article.slack.dto.SlackMessageResponseDtos;
import woowacrew.article.slack.utils.SlackMessageConverter;
import woowacrew.user.domain.User;
import woowacrew.user.service.UserInternalService;

import java.time.LocalDate;
import java.util.List;

@Service
public class SlackMessageService {
    private SlackMessageInternalService slackMessageInternalService;
    private UserInternalService userInternalService;

    public SlackMessageService(SlackMessageInternalService slackMessageInternalService, UserInternalService userInternalService) {
        this.slackMessageInternalService = slackMessageInternalService;
        this.userInternalService = userInternalService;
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

    public SlackMessageResponseDto findRecentlyNoticeMessage() {
        SlackMessage slackMessage = slackMessageInternalService.findRecentlyNoticeMessage();
        return SlackMessageConverter.toDto(slackMessage);
    }

    public void sendBirthdayMessage(LocalDate today) {
        List<User> users = userInternalService.findBirthdayBy(today);
        if (!users.isEmpty()) {
            slackMessageInternalService.sendMessage(SlackMessageConverter.toMessage(users));
        }
    }
}
