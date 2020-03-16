package woowacrew.comment.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import woowacrew.article.free.domain.Article;
import woowacrew.article.free.exception.MisMatchUserException;
import woowacrew.comment.domain.exception.NotValidCommentException;
import woowacrew.common.domain.TimeEntity;
import woowacrew.user.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Comment extends TimeEntity {
    private static final String EMPTY = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @JoinColumn(name = "author")
    @ManyToOne
    private User author;

    @JoinColumn(name = "article")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Article article;

    private Comment() {
    }

    public Comment(User author, String content, Article article) {
        validationCheck(author, content, article);
        this.author = author;
        this.content = content;
        this.article = article;
    }

    private void validationCheck(User author, String content, Article article) {
        if (Objects.isNull(author) || Objects.isNull(article) || EMPTY.equals(content)) {
            throw new NotValidCommentException();
        }
    }

    public void update(User user, String updateContent) {
        if (isAuthor(user)) {
            this.content = updateContent;
            return;
        }
        throw new MisMatchUserException();
    }

    public boolean isAuthor(User user) {
        return author.isSameUser(user);
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Article getArticle() {
        return article;
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
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", author=" + author +
                ", content='" + content + '\'' +
                ", article=" + article +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
