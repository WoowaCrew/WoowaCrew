package woowacrew.article.domain;

import woowacrew.common.domain.TimeEntity;
import woowacrew.user.domain.User;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Article extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ArticleFrom articleFrom;

    @JoinColumn(name = "author")
    @ManyToOne
    private User user;

    private Article() {
    }

    public Article(String title, String content, User user) {
        this.articleFrom = new ArticleFrom(title, content);
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return articleFrom.getTitle();
    }

    public String getContent() {
        return articleFrom.getContent();
    }

    public User getUser() {
        return user;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article that = (Article) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", articleFrom=" + articleFrom +
                ", user=" + user +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
