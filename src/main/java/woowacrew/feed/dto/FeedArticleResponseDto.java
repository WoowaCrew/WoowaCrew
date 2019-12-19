package woowacrew.feed.dto;

import java.time.LocalDateTime;

public class FeedArticleResponseDto {
    private String title;
    private String link;
    private LocalDateTime publishedDate;
    private FeedSourceDto feedSourceDto;

    private FeedArticleResponseDto() {
    }

    public FeedArticleResponseDto(String title, String link, LocalDateTime publishedDate, FeedSourceDto feedSourceDto) {
        this.title = title;
        this.link = link;
        this.publishedDate = publishedDate;
        this.feedSourceDto = feedSourceDto;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public FeedSourceDto getFeedSourceDto() {
        return feedSourceDto;
    }

    public void setFeedSourceDto(FeedSourceDto feedSourceDto) {
        this.feedSourceDto = feedSourceDto;
    }
}
