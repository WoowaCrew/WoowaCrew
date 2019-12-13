package woowacrew.article.free.dto;

public class ArticleRequestDto {
    private String title;
    private String content;

    public ArticleRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
