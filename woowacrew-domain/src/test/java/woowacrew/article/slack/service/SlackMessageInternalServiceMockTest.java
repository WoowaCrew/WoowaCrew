package woowacrew.article.slack.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacrew.article.slack.domain.SlackMessageRepository;
import woowacrew.article.slack.exception.NotFoundRecentlySlackMessageException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SlackMessageInternalServiceMockTest {
    @InjectMocks
    private SlackMessageInternalService slackMessageInternalService;
    @Mock
    private SlackMessageRepository slackMessageRepository;

    @Test
    void 슬랙_공지사항_메세지가_없다면_예외를_발생한다() {
        when(slackMessageRepository.findFirstByChannelOrderByCreatedDateDesc(any()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundRecentlySlackMessageException.class,
                () -> slackMessageInternalService.findRecentlyNoticeMessage());
    }
}