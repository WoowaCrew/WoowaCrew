package woowacrew.keyword.domain;

public class KeywordResponseDto {
    private Long id;
    private String content;
    private Long views;

    private KeywordResponseDto() {
    }

    public KeywordResponseDto(Long id, String content, Long views) {
        this.id = id;
        this.content = content;
        this.views = views;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Long getViews() {
        return views;
    }

    @Override
    public String toString() {
        return "KeywordResponseDto{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", views=" + views +
                '}';
    }
}
