package woowacrew.rss.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import woowacrew.rss.domain.FeedArticleRepository;
import woowacrew.rss.domain.FeedSource;
import woowacrew.rss.domain.FeedSourceRepository;
import woowacrew.rss.dto.FeedRegisterDto;

import java.io.IOException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeedInternalServiceTest {
    @Mock
    private FeedArticleRepository feedArticleRepository;
    @Mock
    private FeedSourceRepository feedSourceRepository;
    @InjectMocks
    private FeedInternalService feedInternalService;

    @Test
    void 정상xml을_입력했을_때_제대로_저장하는지_테스트() throws IOException {
        String sourceUrl = new ClassPathResource("feed.xml").getURL().toString();
        FeedRegisterDto feedRegisterDto = new FeedRegisterDto(sourceUrl, "테스트용 xml");
        FeedSource feedSource = new FeedSource(feedRegisterDto.getSourceUrl(), feedRegisterDto.getDescription());

        when(feedArticleRepository.saveAll(anyList())).thenReturn(new ArrayList<>());
        when(feedSourceRepository.save(any(FeedSource.class))).thenReturn(feedSource);

        FeedSource savedFeedSource = feedInternalService.registerFeedSource(feedRegisterDto);
        assertThat(savedFeedSource.getSourceUrl()).isEqualTo(feedSource.getSourceUrl());
    }
}