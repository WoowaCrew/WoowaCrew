package woowacrew.feed.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.feed.domain.FeedArticle;
import woowacrew.feed.domain.FeedArticleRepository;
import woowacrew.feed.domain.FeedSource;
import woowacrew.feed.domain.FeedSourceRepository;
import woowacrew.feed.dto.FeedRegisterDto;
import woowacrew.feed.utils.FeedConverter;
import woowacrew.feed.utils.RssReader;

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
