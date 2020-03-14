package woowacrew.article;

import woowacrew.article.free.domain.ArticleForm;
import woowacrew.article.free.exception.MisMatchUserException;
import woowacrew.user.domain.User;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Embeddable
public class BasicArticleForm {
    @Embedded
    private ArticleForm articleForm;
    @JoinColumn(name = "author")
    @ManyToOne
    private User author;

    private BasicArticleForm() {
    }

    public BasicArticleForm(String title, String content, User author) {
        this.articleForm = new ArticleForm(title, content);
        this.author = author;
    }

    public void update(User author, String title, String content) {
        checkAuthor(author);

        articleForm.update(title, content);
    }

    public void checkAuthor(User requestAuthor) {
        if (!this.author.equals(requestAuthor)) {
            throw new MisMatchUserException();
        }
    }

    public String getTitle() {
        return articleForm.getTitle();
    }

    public String getContent() {
        return articleForm.getContent();
    }

    public User getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicArticleForm that = (BasicArticleForm) o;
        return Objects.equals(articleForm, that.articleForm) &&
                Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleForm, author);
    }
}
