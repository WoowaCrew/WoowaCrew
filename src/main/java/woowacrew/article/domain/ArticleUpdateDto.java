package woowacrew.article.domain;

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
}
