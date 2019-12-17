package woowacrew.feed.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import woowacrew.feed.domain.FeedArticle;
import woowacrew.feed.domain.FeedSource;
import woowacrew.feed.dto.FeedArticleResponseDto;
import woowacrew.feed.dto.FeedArticleResponseDtos;
import woowacrew.feed.dto.FeedRegisterDto;
import woowacrew.feed.utils.FeedConverter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    @Test
    void findAll() {
        FeedArticle feedArticle = new FeedArticle("title", "link", LocalDateTime.now(), new FeedSource("source","description"));
        Pageable pageable = PageRequest.of(0, 20);
        when(feedInternalService.findAllFeedArticles(pageable)).thenReturn(new PageImpl<>(Arrays.asList(feedArticle)));

        FeedArticleResponseDtos feedArticles = feedService.findAllFeedArticles(pageable);

        assertThat(feedArticles.getPageNumber()).isEqualTo(1);
        assertThat(feedArticles.getTotalPages()).isEqualTo(1);
    }

    @Test
    void updateFeed() {
        String title = "title";
        FeedArticle feedArticle = new FeedArticle(title, "link", LocalDateTime.now(), new FeedSource("source","description"));
        when(feedInternalService.updateFeed()).thenReturn(Collections.singletonList(feedArticle));

        List<FeedArticleResponseDto> feedArticleResponseDtos = feedService.updateFeed();

        assertThat(feedArticleResponseDtos.size()).isEqualTo(1);
        assertThat(feedArticleResponseDtos.get(0).getTitle()).isEqualTo(title);
    }
}