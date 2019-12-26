package woowacrew.feed.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class FeedSourceRequestDto {
    @NotEmpty
    @NotBlank
    private String sourceUrl;
    @NotEmpty
    @NotBlank
    private String description;

    public FeedSourceRequestDto() {
    }

    public FeedSourceRequestDto(String sourceUrl, String description) {
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

    @Override
    public String toString() {
        return "FeedSourceRequestDto{" +
                "sourceUrl='" + sourceUrl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
