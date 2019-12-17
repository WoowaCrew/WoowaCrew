package woowacrew.feed.utils;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import woowacrew.feed.domain.FeedArticle;
import woowacrew.feed.exception.InvalidXmlException;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RssReaderTest {

    @Test
    void xml형식이_아니라면_에러를_던지는지_테스트() {
        String url = "https://vsh123.github.io";
        assertThrows(InvalidXmlException.class, () -> new RssReader(url));
    }

    @Test
    void xml형식이_맞다면_에러없이_통과하는지_테스트() {
        String url = "https://vsh123.github.io/feed.xml";
        assertDoesNotThrow(() -> new RssReader(url));
    }

    @Test
    void feedArticles를_잘_생성하는지_테스트() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("feed.xml");
        RssReader rssReader = new RssReader(classPathResource.getURL().toString());

        List<FeedArticle> feedArticles = rssReader.getFeedArticle();
        assertThat(feedArticles.size()).isEqualTo(3);
    }
}