package woowacrew.rss.util;

import org.junit.jupiter.api.Test;
import woowacrew.rss.exception.InvalidXmlException;

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
}