package woowacrew.article.anonymous.domain;

import woowacrew.article.anonymous.exception.MismatchPasswordException;
import woowacrew.article.free.domain.ArticleForm;
import woowacrew.common.domain.TimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class AnonymousArticle extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ArticleForm articleForm;

    @Embedded
    private AnonymousArticlePassword password;

    private boolean isApproved;

    private AnonymousArticle() {
    }

    public AnonymousArticle(String title, String content, String password) {
        this.articleForm = new ArticleForm(title, content);
        this.password = new AnonymousArticlePassword(password);
        this.isApproved = false;
    }

    public void approve() {
        this.isApproved = true;
    }

    public void update(String title, String content, String password) {
        checkPassword(password);
        this.articleForm.update(title, content);
    }

    public void checkPassword(String password) {
        if (!this.password.match(password)) {
            throw new MismatchPasswordException();
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return articleForm.getTitle();
    }

    public String getContent() {
        return articleForm.getContent();
    }

    public boolean isApproved() {
        return isApproved;
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
        AnonymousArticle that = (AnonymousArticle) o;
        return isApproved == that.isApproved &&
                Objects.equals(id, that.id) &&
                Objects.equals(articleForm, that.articleForm) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, articleForm, password, isApproved);
    }

    @Override
    public String toString() {
        return "AnonymousArticle{" +
                "id=" + id +
                ", articleForm=" + articleForm +
                ", password=" + password +
                ", isApproved=" + isApproved +
                '}';
    }
}
