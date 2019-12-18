package woowacrew.feed.domain;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public class FeedArticleCreator {
    private FeedSource feedSource;
    private SyndFeed feed;

    public FeedArticleCreator(FeedSource feedSource, SyndFeed feed) {
        this.feedSource = feedSource;
        this.feed = feed;
    }

    public FeedArticles createFeedArticles() {
        List<FeedArticle> feedArticles = feed.getEntries().stream()
                .map(entry -> createFeedArticle(entry, feedSource))
                .collect(Collectors.toList());
        return new FeedArticles(feedArticles);
    }

    private FeedArticle createFeedArticle(SyndEntry entry, FeedSource feedSource) {
        LocalDateTime publishedDate = LocalDateTime.ofInstant(entry.getPublishedDate().toInstant(), ZoneId.systemDefault());
        return new FeedArticle(entry.getTitle(), entry.getLink(), publishedDate, feedSource);
    }
}
