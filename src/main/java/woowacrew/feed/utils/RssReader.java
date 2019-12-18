package woowacrew.feed.utils;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import woowacrew.feed.exception.InvalidXmlException;

import java.net.URL;

@Component
public class RssReader {
    private static final Logger log = LoggerFactory.getLogger(RssReader.class);

    public SyndFeed readFeed(String sourceUrl) {
        try {
            URL url = new URL(sourceUrl);
            return new SyndFeedInput().build(new XmlReader(url));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InvalidXmlException();
        }
    }
}
