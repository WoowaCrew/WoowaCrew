package woowacrew.article.slack.dto;

public class SlackMessageResponseDto {
    private Long id;
    private String channel;
    private String author;
    private String content;
    private String downloadLink;
    private String downloadLinkFromSlack;

    private SlackMessageResponseDto() {
    }

    public SlackMessageResponseDto(Long id, String channel, String author, String content) {
        this(id, channel, author, content, null, null);
    }

    public SlackMessageResponseDto(Long id, String channel, String author, String content, String downloadLink, String downloadLinkFromSlack) {
        this.id = id;
        this.channel = channel;
        this.author = author;
        this.content = content;
        this.downloadLink = downloadLink;
        this.downloadLinkFromSlack = downloadLinkFromSlack;
    }

    public Long getId() {
        return id;
    }

    public String getChannel() {
        return channel;
    }

    public String getAuthor() {
        return author;
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

    @Override
    public String toString() {
        return "SlackMessageResponseDto{" +
                "id=" + id +
                ", channel='" + channel + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", downloadLink='" + downloadLink + '\'' +
                ", downloadLinkFromSlack='" + downloadLinkFromSlack + '\'' +
                '}';
    }
}
