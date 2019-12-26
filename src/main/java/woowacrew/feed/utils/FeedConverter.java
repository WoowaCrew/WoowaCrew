package woowacrew.feed.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import woowacrew.feed.domain.FeedArticle;
import woowacrew.feed.domain.FeedSource;
import woowacrew.feed.dto.FeedArticleResponseDto;
import woowacrew.feed.dto.FeedSourceRequestDto;
import woowacrew.feed.dto.FeedSourceResponseDto;

public class FeedConverter {
    private FeedConverter() {
    }

    public static FeedSource toFeedSource(FeedSourceRequestDto registerDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return modelMapper.map(registerDto, FeedSource.class);
    }

    public static FeedSourceResponseDto toFeedSourceResponseDto(FeedSource feedSource) {
        return new ModelMapper().map(feedSource, FeedSourceResponseDto.class);
    }

    public static FeedArticleResponseDto toFeedArticleResponseDto(FeedArticle feedArticle) {
        FeedArticleResponseDto feedArticleResponseDto = new ModelMapper().map(feedArticle, FeedArticleResponseDto.class);
        feedArticleResponseDto.setFeedSourceDto(toFeedSourceResponseDto(feedArticle.getFeedSource()));
        return feedArticleResponseDto;
    }
}
