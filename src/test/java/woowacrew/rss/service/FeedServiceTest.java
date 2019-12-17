package woowacrew.rss.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacrew.rss.domain.FeedSource;
import woowacrew.rss.dto.FeedRegisterDto;
import woowacrew.rss.utils.FeedConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FeedServiceTest {
    @Mock
    private FeedInternalService feedInternalService;
    @InjectMocks
    private FeedService feedService;

    @Test
    void FeedSource가_정상적으로_저장됐다면_RegisterDto를_리턴한다() {
        String url = "url";
        String description = "description";
        FeedRegisterDto feedRegisterDto = new FeedRegisterDto(url, description);
        FeedSource feedSource = FeedConverter.registerDtoToFeedSource(feedRegisterDto);

        when(feedInternalService.registerFeedSource(feedRegisterDto)).thenReturn(feedSource);

        FeedRegisterDto actualRegisterDto = feedService.registerFeedSource(feedRegisterDto);

        assertThat(actualRegisterDto.getSourceUrl()).isEqualTo(url);
        assertThat(actualRegisterDto.getDescription()).isEqualTo(description);
    }
}