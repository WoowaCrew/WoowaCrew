package woowacrew.article.slack.service;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.springframework.stereotype.Service;
import woowacrew.article.slack.domain.SlackConfig;
import woowacrew.article.slack.domain.SlackMessage;
import woowacrew.article.slack.dto.SlackMessageRequestDto;
import woowacrew.article.slack.utils.SlackMessageConverter;

import java.io.IOException;

import static com.ullink.slack.simpleslackapi.impl.SlackSessionFactory.createWebSocketSlackSession;

@Service
public class SlackRequestService {

    private SlackConfig slackConfig;

    public SlackRequestService(SlackConfig slackConfig) {
        this.slackConfig = slackConfig;
    }

    public SlackMessage createSlackMessage(SlackMessageRequestDto slackMessageRequestDto) throws IOException {
        SlackSession session = createWebSocketSlackSession(slackConfig.getToken());
        session.connect();
        SlackChannel channel = session.findChannelById(slackMessageRequestDto.getChannelId());
        SlackUser author = session.findUserById(slackMessageRequestDto.getAuthorId());
        session.disconnect();
        return SlackMessageConverter.toEntity(channel.getName(), author.getUserName(), slackMessageRequestDto);
    }

    public void sendMessage(String message) throws IOException {
        SlackSession session = SlackSessionFactory.createWebSocketSlackSession(slackConfig.getToken());
        session.connect();
        SlackChannel channel = session.findChannelById(slackConfig.getNoticeChannelId());
        session.sendMessage(channel, message);
        session.disconnect();
    }
}
