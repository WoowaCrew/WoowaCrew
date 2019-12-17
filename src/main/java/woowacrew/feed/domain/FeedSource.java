package woowacrew.feed.domain;

import javax.persistence.*;
import java.util.Objects;

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
