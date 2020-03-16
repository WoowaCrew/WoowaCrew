package woowacrew.article.free.domain;

import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.util.Objects;

@Embeddable
public class ArticleForm {
    private String title;
    @Lob
    private String content;

    private ArticleForm() {
    }

    public ArticleForm(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleForm that = (ArticleForm) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content);
    }
}
