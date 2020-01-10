package woowacrew.article.slack.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import woowacrew.article.slack.domain.SlackMessage;
import woowacrew.article.slack.dto.SlackMessageRequestDto;
import woowacrew.article.slack.dto.SlackMessageResponseDto;
import woowacrew.article.slack.dto.SlackMessageResponseDtos;
import woowacrew.article.slack.utils.SlackMessageConverter;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SlackMessageServiceTest {
    @Mock
    private SlackMessageInternalService slackMessageInternalService;

    @InjectMocks
    private SlackMessageService slackMessageService;

    @Test
    @DisplayName("정상적으로 슬랙에서 온 메시지를 저장한다")
    void save() {
        SlackMessageRequestDto slackMessageRequestDto = new SlackMessageRequestDto("test", "testId", "hi", "test.com", "test2.com", false);
        SlackMessage slackMessage = SlackMessageConverter.toEntity("channelName", "authorName", slackMessageRequestDto);

        when(slackMessageInternalService.save(slackMessageRequestDto)).thenReturn(slackMessage);

        SlackMessageResponseDto savedSlackMessage = slackMessageService.save(slackMessageRequestDto);

        assertThat(savedSlackMessage.getAuthor()).isEqualTo("authorName");
        assertThat(savedSlackMessage.getChannel()).isEqualTo("channelName");
        assertThat(savedSlackMessage.getContent()).isEqualTo(slackMessageRequestDto.getContent());
        assertThat(savedSlackMessage.getDownloadLink()).isEqualTo(slackMessageRequestDto.getDownloadLink());
        assertThat(savedSlackMessage.getDownloadLinkFromSlack()).isEqualTo(slackMessageRequestDto.getDownloadLinkFromSlack());
    }

    @Test
    @DisplayName("정상적으로 슬랙 메세지들을 가져온다.")
    void list() {
        int slackMessageSize = 10;
        List<SlackMessage> slackMessages = createSlackMessages(slackMessageSize);

        when(slackMessageInternalService.findAll(any())).thenReturn(new PageImpl<>(slackMessages));

        SlackMessageResponseDtos result = slackMessageService.findAll(PageRequest.of(1, 20));
        assertEquals(result.getSlackMessages().size(), slackMessageSize);
    }

    private List<SlackMessage> createSlackMessages(int slackMessageSize) {
        List<SlackMessage> slackMessages = new ArrayList<>();
        for (int i = 0; i < slackMessageSize; i++) {
            SlackMessage slackMessage = new SlackMessage("channel", "author", "content", "test.com", "test2.com");
            slackMessages.add(slackMessage);
        }
        return slackMessages;
    }
}