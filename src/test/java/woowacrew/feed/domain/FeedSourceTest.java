package woowacrew.feed.domain;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class FeedSourceTest {
    @Test
    void FeedArticles를_잘_생성하는지_테스트() throws IOException {
        String sourceUrl = new ClassPathResource("feed.xml").getURL().toString();
        FeedSource feedSource = new FeedSource(sourceUrl, "description");

        FeedArticles feedArticles = feedSource.createFeedArticles();

        assertThat(feedArticles.getFeedArticles().size()).isEqualTo(3);
    }
}