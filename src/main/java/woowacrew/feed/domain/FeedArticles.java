package woowacrew.feed.domain;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FeedArticles {
    private final List<FeedArticle> feedArticles;

    public FeedArticles(List<FeedArticle> feedArticles) {
        this.feedArticles = feedArticles;
    }

    public List<FeedArticle> getFeedArticles() {
        return Collections.unmodifiableList(feedArticles);
    }

    public List<FeedArticle> getNotDuplicatedFeedArticles(FeedArticles savedArticles) {
        return feedArticles.stream()
                .filter(savedArticles::isNotExistLink)
                .collect(Collectors.toList());
    }

    private boolean isNotExistLink(FeedArticle feedArticle) {
        return feedArticles.stream()
                .noneMatch(savedFeedArticle -> savedFeedArticle.isSameLink(feedArticle));
    }
}
