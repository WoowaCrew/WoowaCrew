package woowacrew.feed.utils;

import org.junit.jupiter.api.Test;
import woowacrew.feed.domain.FeedSource;
import woowacrew.feed.dto.FeedRegisterDto;

import static org.assertj.core.api.Assertions.assertThat;

class FeedConverterTest {

    @Test
    void registerDtoToFeedSource() {
        String url = "url";
        String description = "description";
        FeedRegisterDto feedRegisterDto = new FeedRegisterDto(url, description);
        FeedSource feedSource = FeedConverter.registerDtoToFeedSource(feedRegisterDto);

        assertThat(feedSource.getSourceUrl()).isEqualTo(url);
        assertThat(feedSource.getDescription()).isEqualTo(description);
    }

    @Test
    void feedSourceToRegisterDto() {
        String url = "url";
        String description = "description";
        FeedSource feedSource = new FeedSource(url, description);
        FeedRegisterDto feedRegisterDto = FeedConverter.feedSourceToRegisterDto(feedSource);


        assertThat(feedRegisterDto.getSourceUrl()).isEqualTo(url);
        assertThat(feedRegisterDto.getDescription()).isEqualTo(description);
    }
}