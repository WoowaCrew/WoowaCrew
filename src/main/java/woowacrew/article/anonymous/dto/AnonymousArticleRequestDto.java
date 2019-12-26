package woowacrew.article.anonymous.dto;

public class AnonymousArticleRequestDto {

    private String title;
    private String content;
    private String signingKey;

    public AnonymousArticleRequestDto(String title, String content, String signingKey) {
        this.title = title;
        this.content = content;
        this.signingKey = signingKey;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSigningKey() {
        return signingKey;
    }

    @Override
    public String toString() {
        return "AnonymousArticleRequestDto{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", signingKey='" + signingKey + '\'' +
                '}';
    }
}
