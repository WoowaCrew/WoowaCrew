package woowacrew.feed.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class FeedSourceUpdateRequestDto {
    @NotEmpty
    @NotBlank
    private String description;

    private FeedSourceUpdateRequestDto() {
    }

    public FeedSourceUpdateRequestDto(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "FeedSourceUpdateRequestDto{" +
                "description='" + description + '\'' +
                '}';
    }
}
