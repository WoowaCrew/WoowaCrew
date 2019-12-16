package woowacrew.comment.domain;

import woowacrew.article.AbstractArticle;
import woowacrew.article.free.exception.MisMatchUserException;
import woowacrew.comment.domain.exception.NotValidCommentException;
import woowacrew.common.domain.TimeEntity;
import woowacrew.user.domain.User;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

public class Comment extends TimeEntity {
    private static final String EMPTY = "";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    private User author;

    @ManyToOne
    private AbstractArticle article;

    private Comment() {}

    public Comment(User author, String content, AbstractArticle article) {
        ValidationCheck(author, content, article);
        this.author = author;
        this.content = content;
        this.article = article;
    }

    private void ValidationCheck(User author, String content, AbstractArticle article) {
        if (Objects.isNull(author) || Objects.isNull(article) || EMPTY.equals(content)) {
            throw new NotValidCommentException();
        }
    }

    public void update(User user, String updateContent) {
        if (isSameAuthor(user)) {
            this.content = updateContent;
            return;
        }
        throw new MisMatchUserException();
    }

    private boolean isSameAuthor(User user) {
        //todo User에게 물어보면 어떨까?  isSameUser() 라던
        return author.equals(user);
    }

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public AbstractArticle getArticle() {
        return article;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id.equals(comment.id);
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
