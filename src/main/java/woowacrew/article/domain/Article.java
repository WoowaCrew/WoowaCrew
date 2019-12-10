package woowacrew.article.domain;

import woowacrew.article.exception.MisMatchUserException;
import woowacrew.common.domain.TimeEntity;
import woowacrew.user.domain.User;

import javax.persistence.*;
import java.util.Date;

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
