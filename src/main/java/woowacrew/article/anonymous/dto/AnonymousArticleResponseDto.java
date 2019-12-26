package woowacrew.article.anonymous.dto;

import java.time.LocalDateTime;

public class AnonymousArticleResponseDto {

    private Long anonymousArticleId;
    private String title;
    private String content;
    private boolean isApproved;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    private AnonymousArticleResponseDto() {
    }

    public AnonymousArticleResponseDto(Long anonymousArticleId,
                                       String title, String content,
                                       boolean isApproved,
                                       LocalDateTime createdDate,
                                       LocalDateTime lastModifiedDate) {
        this.anonymousArticleId = anonymousArticleId;
        this.title = title;
        this.content = content;
        this.isApproved = isApproved;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
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
