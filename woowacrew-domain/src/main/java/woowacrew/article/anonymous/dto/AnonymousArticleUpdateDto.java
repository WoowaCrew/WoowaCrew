package woowacrew.article.anonymous.dto;

public class AnonymousArticleUpdateDto {

    private String title;
    private String content;
    private String signingKey;

    public AnonymousArticleUpdateDto(String title, String content, String signingKey) {
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
        return "AnonymousArticleUpdateDto{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", signingKey='" + signingKey + '\'' +
                '}';
    }
}
