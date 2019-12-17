package woowacrew.feed.dto;

public class FeedSourceDto {
    private String sourceUrl;
    private String description;

    public FeedSourceDto() {
    }

    public FeedSourceDto(String sourceUrl, String description) {
        this.sourceUrl = sourceUrl;
        this.description = description;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
