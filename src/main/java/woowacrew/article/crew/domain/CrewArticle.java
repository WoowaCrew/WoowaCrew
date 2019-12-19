package woowacrew.article.crew.domain;

import woowacrew.article.BasicArticleForm;
import woowacrew.common.domain.TimeEntity;
import woowacrew.user.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class CrewArticle extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private BasicArticleForm basicArticleForm;

    private CrewArticle() {
    }

    public CrewArticle(String title, String content, User author) {
        this.basicArticleForm = new BasicArticleForm(title, content, author);
    }

    public void update(User author, String title, String content) {
        basicArticleForm.update(author, title, content);
    }

    public void checkAuthor(User requestAuthor) {
        basicArticleForm.checkAuthor(requestAuthor);
    }

    public boolean isAccessible(User requestAuthor) {
        User author = basicArticleForm.getAuthor();
        return author.isSameDegree(requestAuthor);
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
        CrewArticle that = (CrewArticle) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CrewArticle{" +
                "id=" + id +
                ", basicArticleForm=" + basicArticleForm +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
