package woowacrew.feed.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import woowacrew.feed.domain.FeedArticle;
import woowacrew.feed.domain.FeedSource;
import woowacrew.feed.dto.FeedArticleResponseDto;
import woowacrew.feed.dto.FeedSourceDto;

public class FeedConverter {
    public static FeedSource toFeedSource(FeedSourceDto registerDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return modelMapper.map(registerDto, FeedSource.class);
    }

    public static FeedSourceDto toFeedSourceDto(FeedSource feedSource) {
        return new ModelMapper().map(feedSource, FeedSourceDto.class);
    }

        public static FeedArticleResponseDto toFeedArticleResponseDto(FeedArticle feedArticle) {
        FeedArticleResponseDto feedArticleResponseDto = new ModelMapper().map(feedArticle, FeedArticleResponseDto.class);
        feedArticleResponseDto.setFeedSourceDto(toFeedSourceDto(feedArticle.getFeedSource()));
        return feedArticleResponseDto;
    }
}
