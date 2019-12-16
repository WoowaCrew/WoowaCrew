package woowacrew.rss.util;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import woowacrew.rss.exception.InvalidXmlException;

import java.net.URL;

public class RssReader {
    private SyndFeed feed;

    public RssReader(String url) {
        this.feed = getFeed(url);
    }

    private SyndFeed getFeed(String url) {
        try {
            URL feedUrl = new URL(url);
            SyndFeedInput input = new SyndFeedInput();
            return input.build(new XmlReader(feedUrl));
        } catch (Exception e) {
            throw new InvalidXmlException();
        }
    }
}
