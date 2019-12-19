package woowacrew.feed.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FeedArticlesTest {
    private FeedArticles feedArticles;

    @BeforeEach
    void setUp() {
        feedArticles = new FeedArticles(Arrays.asList(
                new FeedArticle("title", "link1", LocalDateTime.now(), null),
                new FeedArticle("title", "link2", LocalDateTime.now(), null),
                new FeedArticle("title", "link3", LocalDateTime.now(), null),
                new FeedArticle("title", "link4", LocalDateTime.now(), null),
                new FeedArticle("title", "link5", LocalDateTime.now(), null)
        ));
    }

    @Test
    void 중복되는_FeedArticles들을_제외하고_리턴한다() {
        FeedArticles testFeedArtcles = new FeedArticles(Collections.singletonList(
                new FeedArticle("title", "link1", LocalDateTime.now(), null)
        ));
        List<FeedArticle> saveFeedArtcles = feedArticles.getNotDuplicatedFeedArticles(testFeedArtcles);

        assertThat(saveFeedArtcles.size()).isEqualTo(4);
        assertThat(saveFeedArtcles.get(0).getLink()).isNotEqualTo("link1");
    }
}