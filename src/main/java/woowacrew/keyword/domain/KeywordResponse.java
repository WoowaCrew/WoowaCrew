package woowacrew.keyword.domain;

public class KeywordResponse {
    private Long id;
    private String content;
    private Long views;

    public KeywordResponse(Long id, String content, Long views) {
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
}
