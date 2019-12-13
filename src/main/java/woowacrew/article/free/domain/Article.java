package woowacrew.article.free.domain;

import woowacrew.article.AbstractArticle;
import woowacrew.article.free.exception.MisMatchUserException;
import woowacrew.user.domain.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Article extends AbstractArticle {

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
        checkAuthor(user);

        articleForm.updateArticle(title, content);
    }

    public void checkAuthor(User user) {
        if (!this.user.equals(user)) {
            throw new MisMatchUserException();
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

    public User getUser() {
        return user;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getLastModifiedDate() {
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
