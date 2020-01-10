package woowacrew.article.slack.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class SlackMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String channel;

    @Column(nullable = false)
    private String author;

    @Lob
    private String content;

    @Embedded
    private SlackFile slackFile;

    private SlackMessage() {
    }

    public SlackMessage(String channel, String author, String content, String downloadLink, String downloadLinkFromSlack) {
        this.channel = channel;
        this.author = author;
        this.content = content;
        this.slackFile = new SlackFile(downloadLink, downloadLinkFromSlack);
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
        return slackFile.getDownloadLink();
    }

    public String getDownloadLinkFromSlack() {
        return slackFile.getDownloadLinkFromSlack();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SlackMessage that = (SlackMessage) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
