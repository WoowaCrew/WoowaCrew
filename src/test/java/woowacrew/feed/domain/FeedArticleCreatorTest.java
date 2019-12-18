package woowacrew.feed.domain;

import com.rometools.rome.feed.synd.SyndFeed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import woowacrew.feed.utils.RssReaderComponent;

import static org.assertj.core.api.Assertions.assertThat;

class FeedArticleCreatorTest {
    private final RssReaderComponent rssReaderComponent = new RssReaderComponent();

    private String sourceUrl = "https//vsh123.github.io/feed.xml";
    private String description = "description";

    private FeedArticleCreator feedArticleCreator;

    @BeforeEach
    void setUp() {
        SyndFeed feed = rssReaderComponent.readFeed(sourceUrl);
        FeedSource feedSource = new FeedSource(sourceUrl, description);

        feedArticleCreator = new FeedArticleCreator(feedSource, feed);
    }

    @Test
    void FeedArticles들을_잘_생성하는지_테스트() {
        FeedArticles feedArticles = feedArticleCreator.createFeedArticles();

        assertThat(feedArticles).isNotNull();
    }
}