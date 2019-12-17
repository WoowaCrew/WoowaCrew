package woowacrew.feed.dto;

public class FeedRegisterDto {
    private String sourceUrl;
    private String description;

    public FeedRegisterDto() {
    }

    public FeedRegisterDto(String sourceUrl, String description) {
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
