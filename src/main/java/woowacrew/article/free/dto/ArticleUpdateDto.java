package woowacrew.article.free.dto;

import java.util.Objects;

public class ArticleUpdateDto {
    private Long articleId;
    private String title;
    private String content;

    public ArticleUpdateDto(Long articleId, String title, String content) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
    }

    public Long getArticleId() {
        return articleId;
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
        ArticleUpdateDto that = (ArticleUpdateDto) o;
        return Objects.equals(articleId, that.articleId) &&
                Objects.equals(title, that.title) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleId, title, content);
    }
}
