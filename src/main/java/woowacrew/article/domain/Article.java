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
    private ArticleForm articleForm;

    @JoinColumn(name = "author")
    @ManyToOne
    private User user;

    private Article() {
    }

    public Article(String title, String content, User user) {
        this.articleForm = new ArticleForm(title, content);
        this.user = user;
    }

    public void update(User user, String title, String content) {
        if (this.user.equals(user)) {
            articleForm.updateArticle(title, content);
        }

        throw new IllegalArgumentException();
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
                ", articleForm=" + articleForm +
                ", user=" + user +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
