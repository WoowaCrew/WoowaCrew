package woowacrew.rss.domain;

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

    public FeedArticle(String title, String link, LocalDateTime publishedDate) {
        this.title = title;
        this.link = link;
        this.publishedDate = publishedDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedArticle that = (FeedArticle) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(link, that.link) &&
                Objects.equals(publishedDate, that.publishedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, link, publishedDate);
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
