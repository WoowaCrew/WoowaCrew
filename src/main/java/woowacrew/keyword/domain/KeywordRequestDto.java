package woowacrew.keyword.domain;

public class KeywordRequestDto {
    private String content;

    public KeywordRequestDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "KeywordRequestDto{" +
                "content='" + content + '\'' +
                '}';
    }
}
