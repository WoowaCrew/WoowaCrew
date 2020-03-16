package woowacrew.article.free.domain;

import woowacrew.article.BasicArticleForm;
import woowacrew.common.domain.TimeEntity;
import woowacrew.user.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Article extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private BasicArticleForm basicArticleForm;

    private Article() {
    }

    public Article(String title, String content, User author) {
        this.basicArticleForm = new BasicArticleForm(title, content, author);
    }

    public void update(User author, String title, String content) {
        basicArticleForm.update(author, title, content);
    }

    public void checkAuthor(User requestAuthor) {
        basicArticleForm.checkAuthor(requestAuthor);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return basicArticleForm.getTitle();
    }

    public String getContent() {
        return basicArticleForm.getContent();
    }

    public User getAuthor() {
        return basicArticleForm.getAuthor();
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", basicArticleForm=" + basicArticleForm +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
