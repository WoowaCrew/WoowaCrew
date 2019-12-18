package woowacrew.article.anonymous.domain;

import woowacrew.article.anonymous.exception.MismatchPasswordException;
import woowacrew.article.free.domain.ArticleForm;
import woowacrew.common.domain.TimeEntity;

import javax.persistence.*;

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

    public AnonymousArticle(String title, String content, String password) {
        this.articleForm = new ArticleForm(title, content);
        this.password = new AnonymousArticlePassword(password);
        this.isApproved = false;
    }

    public void approve() {
        this.isApproved = true;
    }

    public void update(String password, String title, String content) {
        if (!this.password.match(password)) {
            throw new MismatchPasswordException();
        }
        this.articleForm.updateArticle(title, content);
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
}
