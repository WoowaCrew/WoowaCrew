package woowacrew.feed.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacrew.article.free.exception.InvalidPageRequstException;
import woowacrew.feed.domain.*;
import woowacrew.feed.dto.FeedSourceRequestDto;
import woowacrew.feed.dto.FeedSourceUpdateRequestDto;
import woowacrew.feed.exception.AlreadyExistSourceUrlException;
import woowacrew.feed.exception.NotFoundFeedSourceException;
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

    @CacheEvict(value = "feed", allEntries = true)
    public FeedSource registerFeedSource(FeedSourceRequestDto feedSourceRequestDto) {
        if (isExistUrl(feedSourceRequestDto.getSourceUrl())) {
            throw new AlreadyExistSourceUrlException();
        }

        FeedSource feedSource = feedSourceRepository.save(FeedConverter.toFeedSource(feedSourceRequestDto));
        saveNewFeedArticles(feedSource);

        return feedSource;
    }

    private boolean isExistUrl(String sourceUrl) {
        return feedSourceRepository.existsBySourceUrl(sourceUrl);
    }

    @Transactional(readOnly = true)
    public List<FeedSource> findAllFeedSources() {
        return feedSourceRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "feed", key = "#pageable.pageNumber")
    public Page<FeedArticle> findAllFeedArticles(Pageable pageable) {
        if (pageable.getPageSize() != DEFAULT_ARTICLE_PAGE_SIZE) {
            throw new InvalidPageRequstException();
        }
        return feedArticleRepository.findAll(pageable);
    }

    @CacheEvict(value = "feed", allEntries = true)
    public List<FeedArticle> updateFeedArticles() {
        return feedSourceRepository.findAll().stream()
                .map(this::saveNewFeedArticles)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @CacheEvict(value = "feed", allEntries = true)
    public void deleteFeedSource(Long feedSourceId) {
        feedSourceRepository.deleteById(feedSourceId);
    }

    private List<FeedArticle> saveNewFeedArticles(FeedSource feedSource) {
        FeedArticles feedArticles = feedSource.createFeedArticles();
        FeedArticles savedFeedArticles = new FeedArticles(feedArticleRepository.findByFeedSource(feedSource));
        return feedArticleRepository.saveAll(feedArticles.getNotDuplicatedFeedArticles(savedFeedArticles));
    }

    public FeedSource updateFeedSourceDescription(Long feedSourceId, FeedSourceUpdateRequestDto updateRequestDto) {
        FeedSource feedSource = feedSourceRepository.findById(feedSourceId)
                .orElseThrow(NotFoundFeedSourceException::new);

        feedSource.updateDescription(updateRequestDto.getDescription());

        return feedSource;
    }

    @Transactional(readOnly = true)
    public Page<FeedArticle> findAll(Specification<FeedArticle> specification, Pageable pageable) {
        if (pageable.getPageSize() != DEFAULT_ARTICLE_PAGE_SIZE) {
            throw new InvalidPageRequstException();
        }
        return feedArticleRepository.findAll(specification, pageable);
    }
}
