package woowacrew.rss.utils;

import org.junit.jupiter.api.Test;
import woowacrew.rss.domain.FeedSource;
import woowacrew.rss.dto.FeedRegisterDto;

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
}