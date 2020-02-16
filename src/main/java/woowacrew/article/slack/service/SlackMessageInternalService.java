package woowacrew.article.slack.service;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.article.slack.domain.SlackConfig;
import woowacrew.article.slack.domain.SlackMessage;
import woowacrew.article.slack.domain.SlackMessageRepository;
import woowacrew.article.slack.dto.SlackMessageRequestDto;
import woowacrew.article.slack.exception.CreateSlackMessageFailException;
import woowacrew.article.slack.exception.NotFoundRecentlySlackMessageException;
import woowacrew.article.slack.exception.NotFoundSlackMessageException;
import woowacrew.article.slack.utils.SlackMessageConverter;
import woowacrew.search.exception.SendSlackBirthdayMessageFailException;

import java.io.IOException;

import static com.ullink.slack.simpleslackapi.impl.SlackSessionFactory.createWebSocketSlackSession;

@Service
@Transactional
public class SlackMessageInternalService {
    private static final Logger logger = LoggerFactory.getLogger(SlackMessageInternalService.class);
    private static final String NOTICE_CHANNEL_NAME = "전체-공지사항";

    private SlackConfig slackConfig;
    private SlackMessageRepository slackMessageRepository;

    public SlackMessageInternalService(SlackConfig slackConfig, SlackMessageRepository slackMessageRepository) {
        this.slackConfig = slackConfig;
        this.slackMessageRepository = slackMessageRepository;
    }

    public SlackMessage save(SlackMessageRequestDto slackMessageRequestDto) {
        try {
            SlackMessage slackMessage = createSlackMessage(slackMessageRequestDto);
            return slackMessageRepository.save(slackMessage);
        } catch (Exception e) {
            logger.error("Create slack message fail : ", e);
            throw new CreateSlackMessageFailException();
        }
    }

    private SlackMessage createSlackMessage(SlackMessageRequestDto slackMessageRequestDto) throws IOException {
        SlackSession session = createWebSocketSlackSession(slackConfig.getToken());
        session.connect();
        SlackChannel channel = session.findChannelById(slackMessageRequestDto.getChannelId());
        SlackUser author = session.findUserById(slackMessageRequestDto.getAuthorId());
        session.disconnect();
        return SlackMessageConverter.toEntity(channel.getName(), author.getUserName(), slackMessageRequestDto);
    }

    @Transactional(readOnly = true)
    public Page<SlackMessage> findAll(Pageable pageable) {
        return slackMessageRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public SlackMessage findById(Long id) {
        return slackMessageRepository.findById(id)
                .orElseThrow(NotFoundSlackMessageException::new);
    }

    @Transactional(readOnly = true)
    public SlackMessage findRecentlyMessage() {
        return slackMessageRepository.findFirstByOrderByCreatedDateDesc()
                .orElseThrow(NotFoundRecentlySlackMessageException::new);
    }

    @Transactional(readOnly = true)
    public SlackMessage findRecentlyNoticeMessage() {
        return slackMessageRepository.findFirstByChannelOrderByCreatedDateDesc(NOTICE_CHANNEL_NAME)
                .orElseThrow(NotFoundRecentlySlackMessageException::new);
    }

    public void sendMessage(String message) {
        try {
            SlackSession session = SlackSessionFactory.createWebSocketSlackSession(slackConfig.getToken());
            session.connect();
            SlackChannel channel = session.findChannelById(slackConfig.getChannelId());
            session.sendMessage(channel, message);
            session.disconnect();
        } catch (Exception e) {
            logger.error("Send slack birthday message fail : ", e);
            throw new SendSlackBirthdayMessageFailException();
        }
    }
}
