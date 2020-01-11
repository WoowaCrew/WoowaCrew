package woowacrew.article.slack.dto;

public class SlackMessageRequestDto {
    private String channelId;
    private String authorId;
    private String content;
    private String downloadLink;
    private String downloadLinkFromSlack;
    private boolean isBot;

    public SlackMessageRequestDto(String channelId, String authorId, String content, boolean isBot) {
        this.channelId = channelId;
        this.authorId = authorId;
        this.content = content;
        this.isBot = isBot;
    }

    public SlackMessageRequestDto(String channelId, String authorId, String content, String downloadLink, String downloadLinkFromSlack, boolean isBot) {
        this(channelId, authorId, content, isBot);
        this.downloadLink = downloadLink;
        this.downloadLinkFromSlack = downloadLinkFromSlack;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getContent() {
        return content;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public String getDownloadLinkFromSlack() {
        return downloadLinkFromSlack;
    }

    public boolean isBot() {
        return isBot;
    }
}
