package woowacrew.feed.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class FeedArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String link;
    private LocalDateTime publishedDate;
    @ManyToOne
    private FeedSource feedSource;

    private FeedArticle() {
    }

    public FeedArticle(String title, String link, LocalDateTime publishedDate, FeedSource feedSource) {
        this.title = title;
        this.link = link;
        this.publishedDate = publishedDate;
        this.feedSource = feedSource;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public FeedSource getFeedSource() {
        return feedSource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedArticle that = (FeedArticle) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "FeedArticle{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", publishedDate=" + publishedDate +
                '}';
    }
}
