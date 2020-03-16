package woowacrew.feed.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import woowacrew.feed.domain.FeedArticle;
import woowacrew.feed.domain.FeedSource;
import woowacrew.feed.dto.*;
import woowacrew.feed.utils.FeedConverter;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedService {
    private final FeedInternalService feedInternalService;

    public FeedService(FeedInternalService feedInternalService) {
        this.feedInternalService = feedInternalService;
    }

    public FeedSourceResponseDto registerFeedSource(FeedSourceRequestDto feedSourceRequestDto) {
        return FeedConverter.toFeedSourceResponseDto(feedInternalService.registerFeedSource(feedSourceRequestDto));
    }

    public List<FeedSourceResponseDto> findAllFeedSources() {
        return feedInternalService.findAllFeedSources().stream()
                .map(FeedConverter::toFeedSourceResponseDto)
                .collect(Collectors.toList());
    }

    public FeedArticleResponseDtos findAllFeedArticles(Pageable pageable) {
        Page<FeedArticle> feedArticles = feedInternalService.findAllFeedArticles(pageable);
        List<FeedArticleResponseDto> feedArticleDtos = feedArticles.getContent().stream()
                .map(FeedConverter::toFeedArticleResponseDto)
                .collect(Collectors.toList());
        return new FeedArticleResponseDtos(pageable.getPageNumber(), feedArticles.getTotalPages(), feedArticleDtos);
    }

    public List<FeedArticleResponseDto> updateFeed() {
        return feedInternalService.updateFeedArticles().stream()
                .map(FeedConverter::toFeedArticleResponseDto)
                .collect(Collectors.toList());
    }

    public FeedSourceResponseDto updateFeedSourceDescription(Long feedSourceId, FeedSourceUpdateRequestDto updateRequestDto) {
        FeedSource updatedFeedSource = feedInternalService.updateFeedSourceDescription(feedSourceId, updateRequestDto);
        return FeedConverter.toFeedSourceResponseDto(updatedFeedSource);
    }

    public void deleteFeedSource(Long feedSourceId) {
        feedInternalService.deleteFeedSource(feedSourceId);
    }

    public FeedArticleResponseDtos findSearchedArticles(Specification<FeedArticle> specification, Pageable pageable) {
        Page<FeedArticle> feedArticles = feedInternalService.findAll(specification, pageable);
        List<FeedArticleResponseDto> feedArticleDtos = feedArticles.getContent().stream()
                .map(FeedConverter::toFeedArticleResponseDto)
                .collect(Collectors.toList());
        return new FeedArticleResponseDtos(pageable.getPageNumber(), feedArticles.getTotalPages(), feedArticleDtos);
    }
}
