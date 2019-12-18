package woowacrew.feed.utils;

import org.junit.jupiter.api.Test;
import woowacrew.feed.exception.InvalidXmlException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RssReaderTest {
    @Test
    void xml형식을_잘_읽어오는지_테스트() {
        String sourceUrl = "https://vsh123.github.io/feed.xml";

        assertDoesNotThrow(() -> RssReader.readFeed(sourceUrl));
    }

    @Test
    void xml형식이_아니라면_InvalidXmlException() {
        String sourceUrl = "https://vsh123.github.io";

        assertThrows(InvalidXmlException.class,() -> RssReader.readFeed(sourceUrl));
    }
}