package woowacrew.article.slack.utils;

import org.springframework.data.domain.Page;
import woowacrew.article.slack.domain.SlackMessage;
import woowacrew.article.slack.dto.SlackMessageRequestDto;
import woowacrew.article.slack.dto.SlackMessageResponseDto;
import woowacrew.article.slack.dto.SlackMessageResponseDtos;
import woowacrew.user.domain.User;
import woowacrew.user.utils.UserConverter;

import java.util.List;
import java.util.stream.Collectors;

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
        if (slackMessage.existSlackFile()) {
            return new SlackMessageResponseDto(
                    slackMessage.getId(),
                    slackMessage.getChannel(),
                    slackMessage.getAuthor(),
                    slackMessage.getContent(),
                    slackMessage.getDownloadLink(),
                    slackMessage.getDownloadLinkFromSlack());
        }
        return new SlackMessageResponseDto(slackMessage.getId(), slackMessage.getChannel(), slackMessage.getAuthor(), slackMessage.getContent());
    }

    public static SlackMessageResponseDtos toDtos(Page<SlackMessage> slackMessages) {
        List<SlackMessageResponseDto> convertSlackMessages = slackMessages.stream()
                .map(SlackMessageConverter::toDto)
                .collect(Collectors.toList());
        return new SlackMessageResponseDtos(slackMessages.getNumber(), slackMessages.getTotalPages(), convertSlackMessages);
    }

    public static String toMessage(List<User> users) {
        return "오늘은 " + UserConverter.toMessage(users) + "의 생일 입니다!";
    }
}
