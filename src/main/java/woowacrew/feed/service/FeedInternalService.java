package woowacrew.feed.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.article.free.exception.InvalidPageRequstException;
import woowacrew.feed.domain.FeedArticle;
import woowacrew.feed.domain.FeedArticleRepository;
import woowacrew.feed.domain.FeedSource;
import woowacrew.feed.domain.FeedSourceRepository;
import woowacrew.feed.dto.FeedRegisterDto;
import woowacrew.feed.exception.AlreadyExistSourceUrlException;
import woowacrew.feed.utils.FeedConverter;
import woowacrew.feed.utils.RssReader;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static woowacrew.article.free.service.ArticleInternalService.DEFAULT_ARTICLE_PAGE_SIZE;

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
        if (isExistUrl(feedRegisterDto.getSourceUrl())) {
            throw new AlreadyExistSourceUrlException();
        }
        RssReader rssReader = new RssReader(feedRegisterDto.getSourceUrl());
        List<FeedArticle> feedArticles = rssReader.getFeedArticle();

        feedArticleRepository.saveAll(feedArticles);
        FeedSource feedSource = FeedConverter.registerDtoToFeedSource(feedRegisterDto);

        return feedSourceRepository.save(feedSource);
    }

    @Transactional(readOnly = true)
    public Page<FeedArticle> findAllFeedArticles(Pageable pageable) {
        if (pageable.getPageSize() != DEFAULT_ARTICLE_PAGE_SIZE) {
            throw new InvalidPageRequstException();
        }
        return feedArticleRepository.findAll(pageable);
    }

    private boolean isExistUrl(String sourceUrl) {
        return feedSourceRepository.existsBySourceUrl(sourceUrl);
    }

    public List<FeedArticle> updateFeed() {
        return feedSourceRepository.findAll().stream()
                .map(source -> new RssReader(source.getSourceUrl()).getFeedArticle())
                .flatMap(Collection::stream)
                .filter(feed -> !feedArticleRepository.existsByLink(feed.getLink()))
                .map(feedArticleRepository::save)
                .collect(toList());
    }
}
