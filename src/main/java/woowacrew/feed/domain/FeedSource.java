package woowacrew.feed.domain;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import woowacrew.feed.exception.FeedSourceUpdateException;
import woowacrew.feed.utils.RssReader;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
public class FeedSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String sourceUrl;
    @Column(nullable = false)
    private String description;

    private FeedSource() {
    }

    public FeedSource(String sourceUrl, String description) {
        this.sourceUrl = sourceUrl;
        this.description = description;
    }


    public FeedArticles createFeedArticles() {
        SyndFeed feed = RssReader.readFeed(sourceUrl);
        List<FeedArticle> feedArticles = feed.getEntries().stream()
                .map(this::createFeedArticle)
                .collect(Collectors.toList());
        return new FeedArticles(feedArticles);
    }

    public void updateDescription(String updatedDescription) {
        if (Objects.isNull(updatedDescription)) {
            throw new FeedSourceUpdateException();
        }
        this.description = updatedDescription;
    }

    private FeedArticle createFeedArticle(SyndEntry entry) {
        LocalDateTime publishedDate = LocalDateTime.ofInstant(entry.getPublishedDate().toInstant(), ZoneId.systemDefault());
        return new FeedArticle(entry.getTitle(), entry.getLink(), publishedDate, this);
    }

    public Long getId() {
        return id;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedSource that = (FeedSource) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "FeedSource{" +
                "id=" + id +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
