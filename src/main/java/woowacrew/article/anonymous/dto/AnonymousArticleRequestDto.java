package woowacrew.article.anonymous.dto;

public class AnonymousArticleRequestDto {

    private String title;
    private String content;
    private String password;

    private AnonymousArticleRequestDto() {
    }

    public AnonymousArticleRequestDto(String title, String content, String password) {
        this.title = title;
        this.content = content;
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getPassword() {
        return password;
    }
}
