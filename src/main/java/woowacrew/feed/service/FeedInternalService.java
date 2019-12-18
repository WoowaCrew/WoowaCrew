package woowacrew.feed.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.article.free.exception.InvalidPageRequstException;
import woowacrew.feed.domain.*;
import woowacrew.feed.dto.FeedSourceDto;
import woowacrew.feed.exception.AlreadyExistSourceUrlException;
import woowacrew.feed.utils.FeedConverter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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


    public FeedSource registerFeedSource(FeedSourceDto feedSourceDto) {
        if (isExistUrl(feedSourceDto.getSourceUrl())) {
            throw new AlreadyExistSourceUrlException();
        }

        FeedSource feedSource = feedSourceRepository.save(FeedConverter.toFeedSource(feedSourceDto));
        FeedArticles feedArticles = feedSource.createFeedArticles();

        feedArticleRepository.saveAll(feedArticles.getFeedArticles());

        return feedSource;
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
                .map(this::saveNewFeedArticles)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<FeedArticle> saveNewFeedArticles(FeedSource feedSource) {
        FeedArticles feedArticles = feedSource.createFeedArticles();
        FeedArticles savedFeedArticles = new FeedArticles(feedArticleRepository.findByFeedSource(feedSource));
        return feedArticleRepository.saveAll(feedArticles.getNotDuplicatedFeedArticles(savedFeedArticles));
    }
}
