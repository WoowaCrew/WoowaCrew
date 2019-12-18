package woowacrew.feed.domain;

import com.rometools.rome.feed.synd.SyndFeed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import woowacrew.feed.utils.RssReader;

import static org.assertj.core.api.Assertions.assertThat;

class FeedArticleCreatorTest {
    private final RssReader rssReader = new RssReader();

    private String sourceUrl = "https//vsh123.github.io/feed.xml";
    private String description = "description";

    private FeedArticleCreator feedArticleCreator;

    @BeforeEach
    void setUp() {
        SyndFeed feed = rssReader.readFeed(sourceUrl);
        FeedSource feedSource = new FeedSource(sourceUrl, description);

        feedArticleCreator = new FeedArticleCreator(feedSource, feed);
    }

    @Test
    void FeedArticles들을_잘_생성하는지_테스트() {
        FeedArticles feedArticles = feedArticleCreator.createFeedArticles();

        assertThat(feedArticles).isNotNull();
    }
}