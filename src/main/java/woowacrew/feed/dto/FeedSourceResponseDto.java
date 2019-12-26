package woowacrew.feed.dto;

public class FeedSourceResponseDto {
    private Long id;
    private String sourceUrl;
    private String description;

    public FeedSourceResponseDto() {
    }

    public FeedSourceResponseDto(Long id, String sourceUrl, String description) {
        this.id = id;
        this.sourceUrl = sourceUrl;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "FeedSourceResponseDto{" +
                "id=" + id +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
