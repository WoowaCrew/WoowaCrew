package woowacrew.rss.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.rss.domain.FeedArticle;
import woowacrew.rss.domain.FeedArticleRepository;
import woowacrew.rss.domain.FeedSource;
import woowacrew.rss.domain.FeedSourceRepository;
import woowacrew.rss.dto.FeedRegisterDto;
import woowacrew.rss.utils.FeedConverter;
import woowacrew.rss.utils.RssReader;

import java.util.List;

@Service
@Transactional
public class FeedInternalService {
    private final FeedArticleRepository feedArticleRepository;
    private final FeedSourceRepository feedSourceRepository;

    public FeedInternalService(FeedArticleRepository feedArticleRepository, FeedSourceRepository feedSourceRepository) {
        this.feedArticleRepository = feedArticleRepository;
        this.feedSourceRepository = feedSourceRepository;
    }

    public FeedSource registerFeedSource(FeedRegisterDto feedRegisterDto) {
        RssReader rssReader = new RssReader(feedRegisterDto.getSourceUrl());
        List<FeedArticle> feedArticles = rssReader.getFeedArticle();

        feedArticleRepository.saveAll(feedArticles);
        FeedSource feedSource = FeedConverter.registerDtoToFeedSource(feedRegisterDto);

        return feedSourceRepository.save(feedSource);
    }
}
