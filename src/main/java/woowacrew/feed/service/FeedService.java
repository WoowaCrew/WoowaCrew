package woowacrew.feed.service;

import org.springframework.stereotype.Service;
import woowacrew.feed.dto.FeedRegisterDto;
import woowacrew.feed.utils.FeedConverter;

@Service
public class FeedService {
    private final FeedInternalService feedInternalService;

    public FeedService(FeedInternalService feedInternalService) {
        this.feedInternalService = feedInternalService;
    }

    public FeedRegisterDto registerFeedSource(FeedRegisterDto feedRegisterDto) {
        return FeedConverter.feedSourceToRegisterDto(feedInternalService.registerFeedSource(feedRegisterDto));
    }
}
