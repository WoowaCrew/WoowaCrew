package woowacrew.feed.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import woowacrew.feed.domain.FeedArticle;
import woowacrew.feed.dto.FeedArticleResponseDto;
import woowacrew.feed.dto.FeedArticleResponseDtos;
import woowacrew.feed.dto.FeedRegisterDto;
import woowacrew.feed.utils.FeedConverter;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedService {
    private final FeedInternalService feedInternalService;

    public FeedService(FeedInternalService feedInternalService) {
        this.feedInternalService = feedInternalService;
    }

    public FeedRegisterDto registerFeedSource(FeedRegisterDto feedRegisterDto) {
        return FeedConverter.feedSourceToRegisterDto(feedInternalService.registerFeedSource(feedRegisterDto));
    }

    public FeedArticleResponseDtos findAllFeedArticles(Pageable pageable) {
        Page<FeedArticle> feedArticles = feedInternalService.findAllFeedArticles(pageable);
        List<FeedArticleResponseDto> feedArticleDtos = feedArticles.getContent().stream()
                .map(FeedConverter::toFeedArticleResponseDto)
                .collect(Collectors.toList());
        return new FeedArticleResponseDtos(pageable.getPageNumber(), feedArticles.getTotalPages(), feedArticleDtos);
    }

    public List<FeedArticleResponseDto> updateFeed() {
        return feedInternalService.updateFeed().stream()
                .map(FeedConverter::toFeedArticleResponseDto)
                .collect(Collectors.toList());
    }
}
