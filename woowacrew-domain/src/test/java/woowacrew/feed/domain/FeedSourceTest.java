package woowacrew.feed.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import woowacrew.feed.exception.FeedSourceUpdateException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FeedSourceTest {

    private FeedSource feedSource;

    @BeforeEach
    void setUp() throws IOException {
        String sourceUrl = new ClassPathResource("feed.xml").getURL().toString();
        feedSource = new FeedSource(sourceUrl, "description");
    }

    @Test
    void FeedArticles를_잘_생성하는지_테스트() {
        FeedArticles feedArticles = feedSource.createFeedArticles();

        assertThat(feedArticles.getFeedArticles().size()).isEqualTo(3);
    }

    @Test
    void FeedSource_Description_수정_테스트() {
        String updatedDescription = "updated Description";
        feedSource.updateDescription(updatedDescription);

        assertThat(feedSource.getDescription()).isEqualTo(updatedDescription);
    }

    @Test
    void updatedDescription이_null이라면_예외를_발생() {
        String updatedDescription = null;

        assertThrows(FeedSourceUpdateException.class, () -> feedSource.updateDescription(updatedDescription));
    }
}