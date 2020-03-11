package woowacrew.feed.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class FeedArticleTest {

    @Test
    void 같은Link인지_테스트() {
        FeedArticle feedArticle1 = new FeedArticle("title", "link", LocalDateTime.now(), null);
        FeedArticle feedArticle2 = new FeedArticle("title", "link", LocalDateTime.now(), null);

        assertThat(feedArticle1.isSameLink(feedArticle2)).isTrue();
    }
}