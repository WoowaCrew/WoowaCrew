package woowacrew.article.slack.service;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.article.slack.domain.SlackConfig;
import woowacrew.article.slack.domain.SlackMessage;
import woowacrew.article.slack.domain.SlackMessageRepository;
import woowacrew.article.slack.dto.SlackMessageRequestDto;
import woowacrew.article.slack.exception.CreateSlackMessageFailException;
import woowacrew.article.slack.utils.SlackMessageConverter;

@Service
@Transactional
public class SlackMessageInternalService {

    private SlackConfig slackConfig;
    private SlackMessageRepository slackMessageRepository;

    public SlackMessageInternalService(SlackConfig slackConfig, SlackMessageRepository slackMessageRepository) {
        this.slackConfig = slackConfig;
        this.slackMessageRepository = slackMessageRepository;
    }

    public SlackMessage save(SlackMessageRequestDto slackMessageRequestDto) {
        SlackMessage slackMessage = createSlackMessage(slackMessageRequestDto);
        return slackMessageRepository.save(slackMessage);
    }

    private SlackMessage createSlackMessage(SlackMessageRequestDto slackMessageRequestDto) {
        try {
            SlackSession session = SlackSessionFactory.createWebSocketSlackSession(slackConfig.getToken());
            session.connect();
            SlackChannel channel = session.findChannelById(slackMessageRequestDto.getChannelId());
            SlackUser author = session.findUserById(slackMessageRequestDto.getAuthorId());
            return SlackMessageConverter.toEntity(channel.getName(), author.getUserName(), slackMessageRequestDto);
        } catch (Exception e) {
            throw new CreateSlackMessageFailException();
        }
    }
}
