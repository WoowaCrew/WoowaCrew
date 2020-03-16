package woowacrew.article.slack.dto;

import java.util.List;

public class SlackMessageResponseDtos {
    public static final int INDEX_PARAMETER = 1;
    private int pageNumber;
    private int totalPages;
    private List<SlackMessageResponseDto> slackMessages;

    private SlackMessageResponseDtos() {
    }

    public SlackMessageResponseDtos(int pageNumber, int totalPages, List<SlackMessageResponseDto> slackMessages) {
        this.pageNumber = pageNumber + INDEX_PARAMETER;
        this.totalPages = totalPages;
        this.slackMessages = slackMessages;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<SlackMessageResponseDto> getSlackMessages() {
        return slackMessages;
    }
}
