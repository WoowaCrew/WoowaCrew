package woowacrew.comment.dto;

public class CommentRequestDto {
    private Long articleId;
    private String content;

    public CommentRequestDto(Long articleId, String content) {
        this.articleId = articleId;
        this.content = content;
    }

    public Long getArticleId() {
        return articleId;
    }

    public String getContent() {
        return content;
    }
}
