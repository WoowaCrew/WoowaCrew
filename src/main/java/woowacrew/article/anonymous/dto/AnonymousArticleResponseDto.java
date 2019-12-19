package woowacrew.article.anonymous.dto;

public class AnonymousArticleResponseDto {

    private Long anonymousArticleId;
    private String title;
    private String content;
    private boolean isApproved;

    private AnonymousArticleResponseDto() {
    }

    public AnonymousArticleResponseDto(Long anonymousArticleId, String title, String content, boolean isApproved) {
        this.anonymousArticleId = anonymousArticleId;
        this.title = title;
        this.content = content;
        this.isApproved = isApproved;
    }

    public Long getAnonymousArticleId() {
        return anonymousArticleId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean getIsApproved() {
        return isApproved;
    }

    @Override
    public String toString() {
        return "AnonymousArticleResponseDto{" +
                "anonymousArticleId=" + anonymousArticleId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isApproved=" + isApproved +
                '}';
    }
}
