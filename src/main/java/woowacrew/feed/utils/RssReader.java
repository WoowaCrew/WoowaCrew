package woowacrew.feed.utils;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import woowacrew.feed.domain.FeedArticle;
import woowacrew.feed.exception.InvalidXmlException;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public class RssReader {
    private SyndFeed feed;

    public RssReader(String sourceUrl) {
        this.feed = getFeed(sourceUrl);
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

    public List<FeedArticle> getFeedArticle() {
        return feed.getEntries().stream()
                .map(this::createFeedArticle)
                .collect(Collectors.toList());
    }

    private FeedArticle createFeedArticle(SyndEntry entry) {
        LocalDateTime publishedDate = LocalDateTime.ofInstant(entry.getPublishedDate().toInstant(), ZoneId.systemDefault());
        return new FeedArticle(entry.getTitle(), entry.getLink(), publishedDate);
    }

}
