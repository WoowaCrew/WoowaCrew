package woowacrew.feed.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import woowacrew.feed.domain.FeedSource;
import woowacrew.feed.dto.FeedRegisterDto;

public class FeedConverter {
    public static FeedSource registerDtoToFeedSource(FeedRegisterDto registerDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return modelMapper.map(registerDto, FeedSource.class);
    }

    public static FeedRegisterDto feedSourceToRegisterDto(FeedSource feedSource) {
        return new ModelMapper().map(feedSource, FeedRegisterDto.class);
    }
}
