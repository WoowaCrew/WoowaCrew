package woowacrew.rss.service;

import org.springframework.stereotype.Service;
import woowacrew.rss.dto.FeedRegisterDto;
import woowacrew.rss.utils.FeedConverter;

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
