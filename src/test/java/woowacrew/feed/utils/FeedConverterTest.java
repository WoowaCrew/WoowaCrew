package woowacrew.feed.utils;

import org.junit.jupiter.api.Test;
import woowacrew.feed.domain.FeedArticle;
import woowacrew.feed.domain.FeedSource;
import woowacrew.feed.dto.FeedArticleResponseDto;
import woowacrew.feed.dto.FeedSourceDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class FeedConverterTest {

    @Test
    void registerDtoToFeedSource() {
        String url = "url";
        String description = "description";
        FeedSourceDto feedSourceDto = new FeedSourceDto(url, description);
        FeedSource feedSource = FeedConverter.toFeedSource(feedSourceDto);

        assertThat(feedSource.getSourceUrl()).isEqualTo(url);
        assertThat(feedSource.getDescription()).isEqualTo(description);
    }

    @Test
    void feedSourceToRegisterDto() {
        String url = "url";
        String description = "description";
        FeedSource feedSource = new FeedSource(url, description);
        FeedSourceDto feedSourceDto = FeedConverter.toFeedSourceDto(feedSource);


        assertThat(feedSourceDto.getSourceUrl()).isEqualTo(url);
        assertThat(feedSourceDto.getDescription()).isEqualTo(description);
    }

    @Test
    void toFeedArticleResponseDto() {
        String title = "title";
        String link = "link";
        FeedArticle feedArticle = new FeedArticle(title, link, LocalDateTime.now(), new FeedSource("source","description"));
        FeedArticleResponseDto feedArticleResponseDto = FeedConverter.toFeedArticleResponseDto(feedArticle);

        assertThat(feedArticleResponseDto.getTitle()).isEqualTo(title);
        assertThat(feedArticleResponseDto.getLink()).isEqualTo(link);
    }
}